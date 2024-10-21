package com.sugarweb.chatAssistant.agent.ability.output.audio;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.agent.ability.output.OutputContainer;
import com.sugarweb.chatAssistant.infra.tts.ChatTtsClient;
import com.sugarweb.chatAssistant.infra.tts.TtsAudioFile;
import com.sugarweb.chatAssistant.infra.tts.TtsRequest;
import com.sugarweb.chatAssistant.infra.tts.TtsResponse;
import com.sugarweb.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.media.callback.CallbackMedia;
import uk.co.caprica.vlcj.media.callback.seekable.RandomAccessFileMedia;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

/**
 * SpeakOutputAbility
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class AudioOutputAbility {

    private final ExecutorService executor;

    private Future<?> audioPlayThread = null;

    private Future<?> ttsThread = null;

    private final AudioPlayerComponent audioPlayerComponent;

    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    private final BlockingQueue<AudioContent> audioPlayList = new LinkedBlockingQueue<>();

    private final OutputContainer outputContainer;


    //todo 根据配置文件动态配置
    private final ChatTtsClient ttsClient = new ChatTtsClient();

    public AudioOutputAbility(ExecutorService executor, OutputContainer outputContainer) {
        this.executor = executor;
        this.outputContainer = outputContainer;

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
                    int size = outputContainer.size();
                    //实际并行数量
                    int parallelNum = Math.min(maxTtsThread, size);
                    CountDownLatch countDownLatch = new CountDownLatch(parallelNum);
                    for (int i = 0; i < parallelNum; i++) {
                        //控制顺序
                        AudioContent audioContent = outputContainer.take();
                        Future<String> filePath = executor.submit(() -> {
                            try {
                                return tts(audioContent.getContent());
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

    /**
     * 文本转语音
     * 这是个耗时的方法
     *
     * @param content 文本
     * @return 音频文件路径
     */
    private String tts(String content) {
        // todo 存在[uv_break]问题 https://github.com/jianchang512/ChatTTS-ui/issues/240
        log.info("未清洗数据 content: {}", content);
        // 只允许使用汉字，句号，逗号，感叹号；其他的替换为空白；
        //将特殊符合处理
        content = StrUtil.replace(content, "?", "。");
        content = StrUtil.replace(content, ";", ",");
        content = StrUtil.replace(content, ":", ",");
        content = StrUtil.replace(content, "？", "。");
        content = StrUtil.replace(content, "、", ",");
        //正则表达式
        content = StrUtil.replace(content, "[^\\u4e00-\\u9fa5\\u3002\\uFF0C\\uFF1B\\uFF01]", "");
        log.info("清洗数据后 content: {}", content);

        if (StrUtil.isBlank(content)) {
            throw new IllegalArgumentException("content is blank");
        }
        TtsResponse tts = ttsClient.tts(TtsRequest.builder()
                .voice("1031.pt")
                .text(content)
                .build());
        if (!tts.success()) {
            log.error("ChatTtsClient error: {}", tts.getMsg());
            throw new ServerException(tts.getMsg());
        }
        List<TtsAudioFile> audioFiles = tts.getAudio_files();
        if (audioFiles == null || audioFiles.isEmpty()) {
            log.error("ChatTtsClient error: audioFiles is empty or null");
            throw new ServerException("ChatTtsClient error: audioFiles is empty or null");
        }
        TtsAudioFile first = audioFiles.getFirst();
        String filename = first.getFilename();
        if (StrUtil.isBlank(filename)) {
            log.error("ChatTtsClient error: filename is empty");
            throw new ServerException("ChatTtsClient error: filename is empty");
        }
        return filename;
    }

}
