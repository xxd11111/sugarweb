package com.sugarweb.framework.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * security配置
 *
 * @author xxd
 * @version 1.0
 */
@EnableWebSecurity
@Configuration
public class SecurityAutoConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        List<String> ignoreUrls = new ArrayList<>();
        // http.addFilterBefore(authenticateFilter, UsernamePasswordAuthenticationFilter.class)

        return http
                .csrf(Customizer.withDefaults())
                .headers(headers -> headers
                        .defaultsDisabled()
                        // 缓存控制
                        .cacheControl(Customizer.withDefaults())
                        // HTTP 严格传输安全 （HSTS）
                        .httpStrictTransportSecurity(hsts -> hsts
                                .includeSubDomains(true)
                                .preload(true)
                                .maxAgeInSeconds(31536000)
                        )
                        // X-Frame-选项
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        // X-XSS 保护
                        .xssProtection(xss -> xss
                                .headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                        )
                        // 内容安全策略 （CSP）
                        // .contentSecurityPolicy(csp -> csp
                        //         .policyDirectives("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
                        // )
                        // Referrer Policy
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN)
                        )
                )
                .cors(Customizer.withDefaults())
                .sessionManagement(Customizer.withDefaults())
                .authenticationManager(authentication -> authentication)
                .exceptionHandling(Customizer.withDefaults())
//                .authenticationEntryPoint()
//                .accessDeniedHandler()
                .formLogin(a -> a.loginProcessingUrl("/login").permitAll())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        //openapi url忽略
                        .anyRequest().authenticated()
                        .requestMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                        //测试模式全忽略
                        .requestMatchers("/login").permitAll()
                        //忽略验证的url
                        .requestMatchers(ignoreUrls.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    private static AntPathRequestMatcher[] antMatcher(String... strings) {
        List<AntPathRequestMatcher> requestMatchers = new ArrayList<>();
        for (String string : strings) {
            requestMatchers.add(AntPathRequestMatcher.antMatcher(string));
        }
        AntPathRequestMatcher[] array = requestMatchers.toArray(new AntPathRequestMatcher[0]);
        return array;
    }

    //todo remove 测试用户
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("admin")
                .build()
        );
        manager.createUser(User.withUsername("user")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .roles("user")
                .build()
        );
        return manager;
    }



}
