package com.sugarweb.chatAssistant.agent.ability.think;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ThinkInputQueue
 *
 * @author xxd
 * @since 2024/10/16 21:31
 */
public class ThinkInputQueue {

    private final BlockingQueue<Object> msgList = new LinkedBlockingQueue<>();


    public void add(Object blblMsg) {
        msgList.add(blblMsg);
    }

    public Object poll() {
        return msgList.poll();
    }
}
