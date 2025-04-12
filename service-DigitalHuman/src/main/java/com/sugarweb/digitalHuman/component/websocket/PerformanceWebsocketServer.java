package com.sugarweb.digitalHuman.component.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AutoStageWebsocket
 * <p>
 * demo版本
 *
 * @author xxd
 * @version 1.0
 */
@ServerEndpoint("/performance")
@Slf4j
@Component
public class PerformanceWebsocketServer {

    @Getter
    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 建立连接调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        // 加入Set中
        sessionMap.put(session.getId(), session);
        try {
            session.getBasicRemote().sendText("连接成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("session-{}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessionMap.remove(session.getId());
        log.info("session-onClose-{}， closeReason:{}", session.getId(), closeReason);
    }

}
