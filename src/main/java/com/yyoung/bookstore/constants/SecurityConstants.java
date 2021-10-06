package com.yyoung.bookstore.constants;

public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String SECRET_KEY = "jwt_signature_secret_key";
    public static final int EXPIRATION = 86_400_400;
    public static final String ROLE_CLAIMS = "role";
    public static final String ISSUER = "bookstore";

    public static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/webjars/**"
    };

    public static final String[] PUBLIC_ROUTES = {
            "/users/register",
            "/users/register/checkUsername",
            "/users/login",
            "/books/latest",
            "/books/bestSales",
            "/chat",
            "/statistics/updatePageView"
    };
}
