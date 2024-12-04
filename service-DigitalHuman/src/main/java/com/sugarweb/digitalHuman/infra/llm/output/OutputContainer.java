package com.sugarweb.digitalHuman.infra.llm.output;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * OutputContainer
 *
 * @author xxd
 * @since 2024/10/19 14:45
 */
public class OutputContainer {

    private final BlockingQueue<OutputContent> outputQueue = new LinkedBlockingQueue<>();

    public int size() {
        return outputQueue.size();
    }

    public OutputContent take() throws InterruptedException {
        return outputQueue.take();
    }

    public void put(OutputContent outputContent) throws InterruptedException {
         outputQueue.put(outputContent);
    }
}
