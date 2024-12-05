package com.sugarweb.digitalHuman.infra.llm.output.websocket;

import com.sugarweb.digitalHuman.infra.llm.output.OutputConsumer;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContent;
import com.sugarweb.framework.utils.JsonUtil;
import jakarta.websocket.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket消费者
 *
 * @author xxd
 * @version 1.0
 */
public class WebsocketOutputConsumer implements OutputConsumer {

    private final Map<String, Session> sessionMap;

    public WebsocketOutputConsumer(ConcurrentHashMap<String, Session> sessionMap) {
        this.sessionMap = sessionMap;
    }

    @Override
    public void accept(OutputContent outputContent) {
        sessionMap.forEach((k, v) -> v.getAsyncRemote().sendText(JsonUtil.toJsonStr(outputContent)));
    }


}
