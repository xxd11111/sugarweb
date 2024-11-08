package com.sugarweb.digitalHuman.infra.agent.output.audio;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * OutputContainer
 *
 * @author xxd
 * @since 2024/10/19 14:45
 */
public class AudioOutputContainer {

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
