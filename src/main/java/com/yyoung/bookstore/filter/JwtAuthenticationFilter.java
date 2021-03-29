package com.yyoung.bookstore.filter;

import com.yyoung.bookstore.constants.AuthConstants;
import com.yyoung.bookstore.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(AuthConstants.TOKEN_HEADER);
        if (token == null || !token.startsWith(AuthConstants.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
            return;
        }
        String tokenValue = token.replace(AuthConstants.TOKEN_PREFIX, "");
        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = JwtUtils.getAuthentication(tokenValue);
        } catch (JwtException e) {
            logger.error("Invalid jwt : " + e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
