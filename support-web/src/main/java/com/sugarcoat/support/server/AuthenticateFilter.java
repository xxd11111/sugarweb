package com.sugarcoat.support.server;

import com.google.common.base.Strings;
import com.sugarcoat.protocol.security.AuthenticateService;
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
 * @since 2023/6/27
 */
public class AuthenticateFilter extends OncePerRequestFilter {

    private final AuthenticateService authenticateService;

    public AuthenticateFilter(AuthenticateService tokenService) {
        this.authenticateService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticate = request.getHeader("authenticate");
        if (Strings.isNullOrEmpty(authenticate)){
            authenticateService.authenticate(authenticate);
        }
        filterChain.doFilter(request, response);
    }
}
