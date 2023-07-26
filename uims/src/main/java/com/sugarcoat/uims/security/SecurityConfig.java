package com.sugarcoat.uims.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @description spring security配置
 * @date 2022-10-14
 */
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public AuthenticateFilter authenticateFilter(SessionManager sessionManager) {
        return new AuthenticateFilter(sessionManager);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticateFilter authenticateFilter) throws Exception {

        List<String> urls1 = new ArrayList<>();
        List<String> urls2 = new ArrayList<>();

        return http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable().and()
                .addFilter(authenticateFilter)
                .exceptionHandling()
//                .authenticationEntryPoint()
//                .accessDeniedHandler()
                .and()
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/**").permitAll()
                        .requestMatchers(urls1.toArray(new String[0])).permitAll())
                .authorizeHttpRequests().anyRequest().authenticated().and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("123456").roles("admin").build());
        return manager;
    }

}
