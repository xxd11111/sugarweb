package com.sugarweb.framework.security;

import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    public AuthenticateService authenticateService(AccessTokenRepository accessTokenRepository) {
        return new AuthenticateServiceServiceImpl(accessTokenRepository);
    }

    @Bean
    public AccessTokenRepository accessTokenRepository(RedissonClient redissonClient) {
        return new AccessTokenRepositoryImpl(redissonClient);
    }

    @Bean
    public RefreshTokenRepository refreshTokenRepository(RedissonClient redissonClient) {
        return new RefreshTokenRepositoryImpl(redissonClient);
    }

    @Bean
    public AuthenticateFilter authenticateFilter(AuthenticateService authenticateService) {
        return new AuthenticateFilter(authenticateService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticateFilter authenticateFilter) throws Exception {

        List<String> ignoreUrls = new ArrayList<>();

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
                .authenticationManager(new AuthenticationManager() {
                    @Override
                    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                        return null;
                    }
                })
                .addFilterBefore(authenticateFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(Customizer.withDefaults())
//                .authenticationEntryPoint()
//                .accessDeniedHandler()
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

    //todo remove 测试用户
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("123456").roles("admin").build());
        return manager;
    }



}
