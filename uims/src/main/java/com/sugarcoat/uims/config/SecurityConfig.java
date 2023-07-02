package com.sugarcoat.uims.config;

import com.sugarcoat.uims.domain.security.AuthenticateFilter;
import com.sugarcoat.uims.application.SessionService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author xxd
 * @description spring security配置
 * @date 2022-10-14
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticateFilter authenticateFilter(SessionService sessionService) {
        return new AuthenticateFilter(sessionService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticateFilter authenticateFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated())
                .addFilter(authenticateFilter)
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/").permitAll())
                .cors(httpSecurityCorsConfigurer -> {

                })
                .csrf(httpSecurityCsrfConfigurer -> {
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("123456").roles("admin").build());
        return manager;

    }

}
