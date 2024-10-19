package com.sugarweb.chatAssistant.agent.ability.output;

import com.sugarweb.chatAssistant.agent.ability.output.audio.AudioContent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * OutputContainer
 *
 * @author xxd
 * @since 2024/10/19 14:45
 */
public class OutputContainer {

    private final BlockingQueue<AudioContent> outputQueue = new LinkedBlockingQueue<>();


    public int size() {
        return outputQueue.size();
    }

    public AudioContent take() throws InterruptedException {
        return outputQueue.take();
    }

    public boolean offer(AudioContent audioContent) {
        return outputQueue.offer(audioContent);
    }
}
