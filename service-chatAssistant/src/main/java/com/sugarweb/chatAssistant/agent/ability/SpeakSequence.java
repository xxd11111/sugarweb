package com.sugarweb.chatAssistant.agent.ability;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * SpeakSequence 聊天响应播放序列（按照FIFO）
 *
 * @author xxd
 * @version 1.0
 */
public class SpeakSequence {

    // public final BlockingQueue<MediaContent> playMediaQueue = new PriorityBlockingQueue<>(100,
    //         Comparator.comparingLong(MediaContent::getChatId)
    //                 .thenComparingInt(MediaContent::getSplitId)
    //                 .thenComparing((o1, o2) -> 1));

    public final BlockingQueue<SpeakContent> playMediaQueue = new LinkedBlockingQueue<>();


    public SpeakContent next() throws InterruptedException {
        SpeakContent next = playMediaQueue.take();
        return next;
    }

    public void add(SpeakContent content) throws InterruptedException {
        playMediaQueue.put(content);
    }


}
