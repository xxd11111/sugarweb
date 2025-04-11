package com.sugarweb.digitalHuman.component.llm.output.local;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.component.llm.output.OutputConsumer;
import com.sugarweb.digitalHuman.component.llm.output.OutputContent;
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
 * vlc播放线程
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class LocalOutputConsumer implements OutputConsumer {

    private final ExecutorService executor;

    private Future<?> audioPlayThread = null;

    private final AudioPlayerComponent audioPlayerComponent;

    /**
     * 信号量
     */
    private final Semaphore semaphore = new Semaphore(1);

    private final BlockingQueue<OutputContent> audioPlayList = new LinkedBlockingQueue<>();

    public LocalOutputConsumer(ExecutorService executor) {
        this.executor = executor;

        audioPlayerComponent = new AudioPlayerComponent();
        audioPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                semaphore.release();
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                semaphore.release();
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
        OutputContent outputContent = audioPlayList.take();
        Future<String> filePathFuture = outputContent.getFilePath();
        if (filePathFuture == null) {
            log.error("filePathFuture is null, thoughtId:{}", outputContent.getThoughtId());
            return;
        }
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
        log.info("localFilePath: {}", filePath);
        log.info("thinkId:{},splitId:{}, 语音内容: {}", outputContent.getThoughtId(), outputContent.getSplitId(), outputContent.getContent());
        CallbackMedia media = new RandomAccessFileMedia(new File(filePath));
        //注意此方法是异步执行，调用vlc播放(这一步要严格保证没问题)，否则死锁
        semaphore.acquire();
        audioPlayerComponent.mediaPlayer().media().play(media);
    }

    public void stop() {
        if (audioPlayThread != null) {
            audioPlayThread.cancel(true);
        }
    }

    @Override
    public void accept(OutputContent outputContent) {
        boolean offer = audioPlayList.offer(outputContent);
        if (!offer) {
            log.error("播放队列已满，无法插入新的播放内容。thoughtId:{},splitId:{}, content:{}", outputContent.getThoughtId(), outputContent.getSplitId(), outputContent.getContent());
        }
    }
}
