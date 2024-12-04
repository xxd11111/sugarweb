package com.sugarweb.digitalHuman.controller;

import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.InputContent;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContent;
import com.sugarweb.framework.utils.JsonUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * AutoStageWebsocket
 * <p>
 * demo版本
 * todo 支持多舞台
 *
 * @author xxd
 * @version 1.0
 */
@ServerEndpoint("/performance")
@Slf4j
@Component
public class AutoStageWebsocket extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    public static InputContainer inputContainer;

    public static OutputContainer outputContainer;

    /**
     * 建立连接调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        // 加入Set中
        sessionMap.put(session.getId(), session);
        log.info("session-{}", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        InputContent object = JsonUtil.toObject(message, InputContent.class);
        inputContainer.add(object);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("session-onClose-{}", session.getId());
    }

    public void outputContainer() {
        try {
            OutputContent take = outputContainer.take();
            sessionMap.forEach((k, v) -> v.getAsyncRemote().sendText(JsonUtil.toJsonStr(take)));
        } catch (InterruptedException e) {
            log.error("outputContainer InterruptedException", e);
        }
    }

}
