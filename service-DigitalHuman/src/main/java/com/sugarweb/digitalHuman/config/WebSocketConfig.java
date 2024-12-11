package com.sugarweb.digitalHuman.config;

import cn.hutool.core.collection.CollUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig {

    private static final String SECURITY_KEY = "vavavenaliflavnaldvoewfifp;nvl";

    /**
     * ServerEndpointExporter 作用
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public HttpSessionHandshakeInterceptor webSocketHandshakeInterceptor() {
        return new HttpSessionHandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                List<String> strings = request.getHeaders().get("token");
                if (CollUtil.isEmpty(strings)) {
                    return false;
                } else {
                    String token = strings.getFirst();
                    if (SECURITY_KEY.equals(token)) {
                        super.setCreateSession(true);
                        return super.beforeHandshake(request, response, wsHandler, attributes);
                    }
                }
                return false;
            }
        };
    }

}
