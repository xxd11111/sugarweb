package com.sugarweb.chatAssistant.agent.ability.output.speak;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkOutputAdaptor;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.media.callback.CallbackMedia;
import uk.co.caprica.vlcj.media.callback.seekable.RandomAccessFileMedia;
import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SpeakOutputAbility
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class SpeakOutputAbility {

    private final ExecutorService executor;

    private final AtomicReference<Future<?>> speakThread = new AtomicReference<>(null);

    private final ThinkOutputAdaptor thinkOutputAdaptor;

    private final AudioPlayerComponent audioPlayerComponent;

    private final ReentrantLock speakingLock = new ReentrantLock();
    //信号量
    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);


    public SpeakOutputAbility(ExecutorService executor, ThinkOutputAdaptor thinkOutputAdaptor) {
        this.executor = executor;
        this.thinkOutputAdaptor = thinkOutputAdaptor;

        audioPlayerComponent = new AudioPlayerComponent();
        audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    log.error("Interrupted while waiting for barrier", e);
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    cyclicBarrier.reset();
                    log.error("Barrier broken", e);
                }
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    log.error("Interrupted while waiting for barrier", e);
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    cyclicBarrier.reset();
                    log.error("Barrier broken", e);
                }
            }
        });
    }

    // private void exit(int result) {
    //     // It is not allowed to call back into LibVLC from an event handling thread, so submit() is used
    //     mediaPlayerComponent.mediaPlayer().submit(() -> {
    //         mediaPlayerComponent.mediaPlayer().release();
    //         System.exit(result);
    //     });
    // }

    public void start() {
        Future<?> currentThread = speakThread.get();
        if (currentThread == null || currentThread.isDone()) {
            Future<?> newThread = executor.submit(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        audioPlay();
                    }
                } catch (Exception e) {
                    log.error("Error in speak thread", e);
                }
            });
            speakThread.compareAndSet(currentThread, newThread);
        }
    }
    public void audioPlay() throws InterruptedException {
        SpeakContent speakContent = thinkOutputAdaptor.take();
        Future<String> filePathFuture = speakContent.getFilePath();
        String filePath;
        try {
            filePath = filePathFuture.get();
        } catch (ExecutionException e) {
            log.error("Error getting file path: {}", e.getCause(), e);
            return;
        }
        log.info("filePath: {}", filePath);
        if (StrUtil.isEmpty(filePath)) {
            return;
        }
        try {
            log.info("localFilePath: {}", filePath);
            CallbackMedia media = new RandomAccessFileMedia(new File(filePath));
            //注意此方法是异步执行，调用vlc播放(这一步要严格保证没问题)，否则死锁
            audioPlayerComponent.mediaPlayer().media().play(media);
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            cyclicBarrier.reset();
        }
    }

    public void stop() {
        Future<?> currentThread = speakThread.get();
        if (currentThread != null) {
            currentThread.cancel(true);
            speakThread.compareAndSet(currentThread, null);
        }
    }

}
