package com.sugarweb.digitalHuman.infra.llm.input;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * InputContainer
 * 此类组件应该与InputComponent组件配合使用
 * 作为连接think层与input层之间使用
 *
 * @author xxd
 * @since 2024/10/16 21:31
 */
public class InputContainer {

    private final BlockingQueue<Object> msgList = new LinkedBlockingQueue<>();


    public void add(Object blblMsg) {
        msgList.add(blblMsg);
    }

    public Object poll() {
        return msgList.poll();
    }

}
