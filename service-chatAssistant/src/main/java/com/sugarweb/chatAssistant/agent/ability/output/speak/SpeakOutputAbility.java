package com.sugarweb.chatAssistant.agent.ability.output.speak;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.agent.ability.adaptor.ThinkOutputAdaptor;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
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

    //todo 音频播放异常待处理
    private final AudioPlayerComponent mediaPlayerComponent;

    private final ReentrantLock speakingLock = new ReentrantLock();

    public SpeakOutputAbility(ExecutorService executor, ThinkOutputAdaptor thinkOutputAdaptor) {
        this.executor = executor;
        this.thinkOutputAdaptor = thinkOutputAdaptor;

        mediaPlayerComponent = new AudioPlayerComponent();
        MediaPlayer mediaPlayer = mediaPlayerComponent.mediaPlayer();
        EventApi events = mediaPlayer.events();
        events.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                speakingLock.unlock();
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                speakingLock.unlock();
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
                        output();
                    }
                } catch (Exception e) {
                    log.error("Error in speak thread", e);
                }
            });
            speakThread.compareAndSet(currentThread, newThread);
        }
    }

    public void stop() {
        Future<?> currentThread = speakThread.get();
        if (currentThread != null) {
            currentThread.cancel(true);
            speakThread.compareAndSet(currentThread, null);
        }
    }

    public void output() throws InterruptedException {
        SpeakContent speakContent = thinkOutputAdaptor.take();
        Future<String> filePathFuture = speakContent.getFilePath();
        String filePath;
        try {
            filePath = filePathFuture.get();
            log.info("filePath: {}", filePath);
            mediaPlay(filePath);
        } catch (ExecutionException e) {
            log.error("Error getting file path: {}", e.getCause(), e);
        }
    }

    private void mediaPlay(String localFilePath) {
        if (StrUtil.isEmpty(localFilePath)) {
            return;
        }
        speakingLock.lock();
        try {
            //注意此方法是异步执行，调用vlc播放(这一步要严格保证没问题)，否则死锁
            mediaPlayerComponent.mediaPlayer().media().play(localFilePath);
        } catch (Exception e) {
            log.error("Error playing media: {}", e.getMessage(), e);
            speakingLock.unlock();
        }
    }


}
