package com.sugarweb.digitalHuman.infra.llm.input.websocket;

import com.sugarweb.digitalHuman.infra.llm.input.InputContainer;
import com.sugarweb.digitalHuman.infra.llm.input.InputContent;
import com.sugarweb.framework.utils.JsonUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
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
    private final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    private final Map<String, InputContainer> inputContainerMap = new ConcurrentHashMap<>();

    public void loadInputContainer(String stageId, InputContainer inputContainer) {
        inputContainerMap.put(stageId, inputContainer);
    }

    public void unloadInputContainer(String stageId) {
        inputContainerMap.remove(stageId);
    }

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
        InputContentRequest request = JsonUtil.toObject(message, InputContentRequest.class);
        InputContainer inputContainer = inputContainerMap.get(request.getStageId());
        InputContent inputContent = new InputContent();
        inputContent.setUserId(request.getUserId());
        inputContent.setUsername(request.getUsername());
        inputContent.setContent(request.getContent());
        inputContent.setCreateTime(LocalDateTime.now());
        inputContainer.add(inputContent);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessionMap.remove(session.getId());
        log.info("session-onClose-{}， closeReason:{}", session.getId(), closeReason);
    }

    @Data
    public static class InputContentRequest{

        private String stageId;

        private String userId;

        private String username;

        private String content;

        private LocalDateTime createTime;

    }

}
