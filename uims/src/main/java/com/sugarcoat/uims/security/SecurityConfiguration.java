package com.sugarcoat.uims.security;

import com.sugarcoat.uims.security.session.SessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @description spring security配置
 * @date 2022-10-14
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {


    @Bean
    public AuthenticateFilter authenticateFilter(SessionManager sessionManager) {
        return new AuthenticateFilter(sessionManager);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticateFilter authenticateFilter) throws Exception {

        List<String> ignoreUrls = new ArrayList<>();

        return http.
                csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable().and()
                .authenticationManager(new AuthenticationManager() {
                    @Override
                    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                        return null;
                    }
                })
                .addFilterBefore(authenticateFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
//                .authenticationEntryPoint()
//                .accessDeniedHandler()
                .and()
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        //静态资源放行
                        .requestMatchers(antMatcher("/*.html", "/**/*.html", "/**/*.css", "/**/*.js")).permitAll()
                        //测试模式全忽略
                        .requestMatchers(antMatcher("/**")).permitAll()
                        //忽略验证的url
                        .requestMatchers(antMatcher(ignoreUrls.toArray(new String[0]))).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    private static AntPathRequestMatcher[] antMatcher(String... strings) {
        List<AntPathRequestMatcher> requestMatchers = new ArrayList<>();
        for (String string : strings) {
            requestMatchers.add(AntPathRequestMatcher.antMatcher(string));
        }
        return requestMatchers.toArray(new AntPathRequestMatcher[0]);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("123456").roles("admin").build());
        return manager;
    }

}
