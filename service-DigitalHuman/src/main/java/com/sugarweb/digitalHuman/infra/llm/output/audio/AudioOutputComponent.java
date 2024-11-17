package com.sugarweb.digitalHuman.infra.llm.output.audio;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.infra.llm.tts.ChatTtsModel;
import com.sugarweb.digitalHuman.infra.llm.tts.TtsModel;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.media.callback.CallbackMedia;
import uk.co.caprica.vlcj.media.callback.seekable.RandomAccessFileMedia;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;
import java.util.concurrent.*;

/**
 * SpeakOutputAbility
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class AudioOutputComponent {

    private final ExecutorService executor;

    private Future<?> audioPlayThread = null;

    private Future<?> ttsThread = null;

    private final AudioPlayerComponent audioPlayerComponent;

    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    private final BlockingQueue<AudioContent> audioPlayList = new LinkedBlockingQueue<>();

    private final AudioOutputContainer audioOutputContainer;

    //todo 根据配置文件动态配置
    private final TtsModel ttsModel = new ChatTtsModel("http://127.0.0.1:9966/tts");

    public AudioOutputComponent(ExecutorService executor, AudioOutputContainer audioOutputContainer) {
        this.executor = executor;
        this.audioOutputContainer = audioOutputContainer;

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
        startTts();

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
        AudioContent audioContent = takeAudio();
        Future<String> filePathFuture = audioContent.getFilePath();
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
            log.info("thinkId:{},splitId:{}, 语音内容: {}", audioContent.getThinkId(), audioContent.getSplitId(), audioContent.getContent());
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

    public void putAudio(AudioContent audioContent) throws InterruptedException {
        audioPlayList.put(audioContent);
    }

    public AudioContent takeAudio() throws InterruptedException {
        return audioPlayList.take();
    }


    private void startTts() {
        if (ttsThread != null && !ttsThread.isDone()) {
            return;
        }

        ttsThread = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    //顺序并行处理
                    int maxTtsThread = 2;
                    int size = audioOutputContainer.size();
                    //实际并行数量
                    int parallelNum = Math.min(maxTtsThread, size);
                    CountDownLatch countDownLatch = new CountDownLatch(parallelNum);
                    for (int i = 0; i < parallelNum; i++) {
                        //控制顺序
                        AudioContent audioContent = audioOutputContainer.take();
                        Future<String> filePath = executor.submit(() -> {
                            try {
                                return ttsModel.tts(audioContent.getContent());
                            } finally {
                                //finish
                                countDownLatch.countDown();
                            }
                        });
                        audioContent.setFilePath(filePath);
                        putAudio(audioContent);
                    }
                    //如果此时并行数量大于2，则等待
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    log.error("ttsTask InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

}
