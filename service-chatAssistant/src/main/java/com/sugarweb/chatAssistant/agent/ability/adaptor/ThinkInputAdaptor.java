package com.sugarweb.chatAssistant.agent.ability.adaptor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ThinkInputQueue
 * 此类组件应该与AwareAbility组件配合使用
 * 作为连接think层与aware层之间使用
 *
 * @author xxd
 * @since 2024/10/16 21:31
 */
public class ThinkInputAdaptor {

    private final BlockingQueue<Object> msgList = new LinkedBlockingQueue<>();


    public void add(Object blblMsg) {
        msgList.add(blblMsg);
    }

    public Object poll() {
        return msgList.poll();
    }

}
