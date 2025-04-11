package com.sugarweb.digitalHuman.component.llm.input;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * InputContainer
 * 此类组件应该与InputComponent组件配合使用
 * 作为连接thought层与input层之间使用
 *
 * @author xxd
 * @since 2024/10/16 21:31
 */
public class InputContainer {

    private final BlockingQueue<InputContent> inputContentQueue = new LinkedBlockingQueue<>();

    public void add(InputContent inputContent) {
        inputContentQueue.add(inputContent);
    }

    public InputContent poll() {
        return inputContentQueue.poll();
    }

    public int size(){
        return inputContentQueue.size();
    }

}
