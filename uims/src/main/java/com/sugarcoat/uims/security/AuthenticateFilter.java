package com.sugarcoat.uims.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 认证过滤器
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/27
 */

public class AuthenticateFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    public AuthenticateFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        TokenInfo tokenInfo = new TokenInfo();
        sessionManager.authenticate(tokenInfo);
        filterChain.doFilter(request, response);
    }
}
