package com.sugarweb.chatAssistant.agent.ability.output.speak;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * SpeakSequence 聊天响应播放序列（按照FIFO）
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class SpeakSequence {


    // public final BlockingQueue<MediaContent> playMediaQueue = new PriorityBlockingQueue<>(100,
    //         Comparator.comparingLong(MediaContent::getChatId)
    //                 .thenComparingInt(MediaContent::getSplitId)
    //                 .thenComparing((o1, o2) -> 1));
    public final BlockingQueue<SpeakContent> playMediaQueue = new LinkedBlockingQueue<>();


    public SpeakContent take() throws InterruptedException {
        return playMediaQueue.take();
    }

    public void add(SpeakContent content) throws InterruptedException {
        playMediaQueue.put(content);
    }

}
