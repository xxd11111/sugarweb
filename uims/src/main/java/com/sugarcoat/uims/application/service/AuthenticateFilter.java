package com.sugarcoat.uims.application.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/27
 */

public class AuthenticateFilter extends OncePerRequestFilter {
    private final SecurityService securityService;

    public AuthenticateFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        securityService.authenticate();
        filterChain.doFilter(request, response);
    }
}
