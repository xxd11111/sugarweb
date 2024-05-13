package com.sugarweb.framework.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
@RequiredArgsConstructor
public class SecurityResourceServerConfiguration {

    protected final ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

    private final SugarBearerTokenResolver pigBearerTokenExtractor;

    private final OpaqueTokenIntrospector customOpaqueTokenIntrospector;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> ignoreUrls = new ArrayList<>();
        AntPathRequestMatcher[] antPathRequestMatchers = antMatcher(ignoreUrls);

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(antPathRequestMatchers)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2ResourceServer(
                        oauth2 -> oauth2.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
                                .authenticationEntryPoint(resourceAuthExceptionEntryPoint)
                                .bearerTokenResolver(pigBearerTokenExtractor))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    private static AntPathRequestMatcher[] antMatcher(List<String> ignoreUrls) {
        List<AntPathRequestMatcher> requestMatchers = new ArrayList<>();
        for (String string : ignoreUrls) {
            requestMatchers.add(AntPathRequestMatcher.antMatcher(string));
        }
        return requestMatchers.toArray(new AntPathRequestMatcher[0]);
    }

}
