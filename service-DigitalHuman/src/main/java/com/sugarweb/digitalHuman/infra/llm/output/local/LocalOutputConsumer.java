package com.sugarweb.digitalHuman.infra.llm.output.local;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContent;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.media.callback.CallbackMedia;
import uk.co.caprica.vlcj.media.callback.seekable.RandomAccessFileMedia;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;
import java.util.concurrent.*;

/**
 * LocalOutputConsumer 本地消费者
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class LocalOutputConsumer {

    private final ExecutorService executor;

    private Future<?> audioPlayThread = null;

    private final AudioPlayerComponent audioPlayerComponent;

    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    private final BlockingQueue<OutputContent> audioPlayList = new LinkedBlockingQueue<>();

    public LocalOutputConsumer(ExecutorService executor) {
        this.executor = executor;

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

    public void start() {
        if (audioPlayThread != null && !audioPlayThread.isDone()) {
            return;
        }
        audioPlayThread = executor.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    playNext();
                }
            } catch (Exception e) {
                log.error("Error in speak thread", e);
            }
        });
    }

    private void playNext() throws InterruptedException {
        OutputContent outputContent = takeAudio();
        Future<String> filePathFuture = outputContent.getFilePath();
        String filePath;
        try {
            filePath = filePathFuture.get();
        } catch (ExecutionException e) {
            log.error("Error getting file path: {}", e.getCause(), e);
            return;
        }
        if (StrUtil.isEmpty(filePath)) {
            return;
        }
        try {
            log.info("localFilePath: {}", filePath);
            log.info("thinkId:{},splitId:{}, 语音内容: {}", outputContent.getThinkId(), outputContent.getSplitId(), outputContent.getContent());
            CallbackMedia media = new RandomAccessFileMedia(new File(filePath));
            //注意此方法是异步执行，调用vlc播放(这一步要严格保证没问题)，否则死锁
            audioPlayerComponent.mediaPlayer().media().play(media);
            cyclicBarrier.await();
        } catch (BrokenBarrierException e) {
            cyclicBarrier.reset();
        }
    }

    public void stop() {
        if (audioPlayThread != null) {
            audioPlayThread.cancel(true);
        }
    }

    public OutputContent takeAudio() throws InterruptedException {
        return audioPlayList.take();
    }

}
