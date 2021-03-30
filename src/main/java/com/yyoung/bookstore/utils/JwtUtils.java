package com.yyoung.bookstore.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yyoung.bookstore.constants.SecurityConstants.*;

public class JwtUtils {
    public static String createToken(String username, String id, List<String> roles) {
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + EXPIRATION);
        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .claim(ROLE_CLAIMS, String.join(",", roles))
                .setId(id)
                .setIssuer(ISSUER)
                .setIssuedAt(createdDate)
                .setSubject(username)
                .setExpiration(expirationDate)
                .compact();
        return TOKEN_PREFIX + token;
    }

    public static String getId(String token) {
        Claims claims = getClaims(token);
        return claims.getId();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = getClaims(token);
        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        String userName = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
    }

    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        String role = (String) claims.get(ROLE_CLAIMS);
        return Arrays.stream(role.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
