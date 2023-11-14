package com.sugarcoat.support.orm.tenant;

import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 租户拦截器
 *
 * @author 许向东
 * @date 2023/11/14
 */
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader("tenantId");
        TenantContext.setTenantId(tenantId);
        if ("root".equals(tenantId)) {
            TenantContext.setTenantIgnore(true);
        }
        filterChain.doFilter(request, response);
        TenantContext.remove();
    }


}
