package com.sugarweb.chatAssistant.agent.ability;

import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// @ServerEndpoint("/msg")
@Slf4j
// @Component
public class MsgOutputAbility {

    private static final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();

    private static final AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 建立连接调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        // 加入Set中
        sessions.put("1", session);
        // 在线数增加
        onlineCount.getAndIncrement();
        log.info("session-{},online-count-{}", session.getId(), onlineCount.get());
    }


    /**
     * 收到客户端消息后调用的方法
     * onMessage 方法被@OnMessage所注解。这个注解定义了当服务器接收到客户端发送的消息时所调用的方法。
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(Session sender, String message) throws Exception {
        Session receiver = sessions.get("1");
        if (receiver != null) {
            receiver.getBasicRemote().sendText(message);
        }
    }


    /**
     * 关闭连接调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        // 从Set中删除
        sessions.remove("1");
        // 在线数减少
        onlineCount.getAndDecrement();
        log.info("session-{},down-line-count-{}", session.getId(), onlineCount.get());
    }

    /**
     * 发生错误调用的方法
     */
    @OnError
    public void onError(Session session, Throwable throwable) throws Exception {
        log.error("Web Stock Error", throwable);
        session.getBasicRemote().sendText(throwable.getMessage());
    }

    /**
     * 向客户端发送消息
     */
    public void sendMessage(String sid, String message) throws IOException {
        Session session = sessions.get(sid);
        //同步发消息
        session.getBasicRemote().sendText(message);
    }

}
