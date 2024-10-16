package com.sugarweb.chatAssistant.agent.ability.speak;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.infra.tts.ChatTtsClient;
import com.sugarweb.chatAssistant.infra.tts.TtsAudioFile;
import com.sugarweb.chatAssistant.infra.tts.TtsRequest;
import com.sugarweb.chatAssistant.infra.tts.TtsResponse;
import com.sugarweb.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SpeakAbility
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class SpeakAbility {

    /**
     * 优先级队列，保证播放顺序,后来的排后面
     */
    private final SpeakSequence speakSequence = new SpeakSequence();

    private final ExecutorService executor;

    private Future<?> speakThread = null;

    private AudioPlayerComponent mediaPlayerComponent = null;

    private final ReentrantLock speakingLock = new ReentrantLock();

    public SpeakAbility(ExecutorService executor) {
        this.executor = executor;
    }

    public void init() {
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

    public void start() {
        if (speakThread == null || speakThread.isDone()) {
            speakThread = executor.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        SpeakContent next = speakSequence.next();
                        Future<String> filePathFuture = next.getFilePath();
                        String filePath = filePathFuture.get();
                        //等上个音频播放结束
                        speakingLock.lock();
                        //注意此方法是异步执行，调用vlc播放
                        mediaPlay(filePath);
                    } catch (InterruptedException e) {
                        log.error("SpeakAbility.start InterruptedException", e);
                        speakingLock.unlock();
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        speakingLock.unlock();
                        log.error("SpeakAbility.start ExecutionException", e);
                    }
                }
            });
        }
    }

    public void stop() {
        if (speakThread != null) {
            speakThread.cancel(true);
            speakThread = null;
        }
    }

    public void addSpeakContent(long chatId, int spiltId, String content) throws InterruptedException {
        Future<String> submit = executor.submit(() -> tts(content));
        speakSequence.add(SpeakContent.builder()
                .chatId(chatId)
                .splitId(spiltId)
                .content(content)
                .filePath(submit)
                .build());
    }

    /**
     * 文本转语音
     * 这是个耗时的方法
     *
     * @param content 文本
     * @return 音频文件路径
     */
    private String tts(String content) {
        if (StrUtil.isBlank(content)) {
            throw new IllegalArgumentException("content is blank");
        }
        ChatTtsClient chatTtsClient = new ChatTtsClient();
        TtsResponse tts = chatTtsClient.tts(TtsRequest.builder()
                .text(content)
                .build());
        if (!tts.success()) {
            log.error("ChatTtsClient error: {}", tts.getMsg());
            throw new ServerException(tts.getMsg());
        }
        List<TtsAudioFile> audioFiles = tts.getAudio_files();
        if (audioFiles.isEmpty()) {
            log.error("ChatTtsClient error: audioFiles is empty");
            throw new ServerException("ChatTtsClient error: audioFiles is empty");
        }
        TtsAudioFile first = audioFiles.getFirst();
        String filename = first.getFilename();
        if (StrUtil.isBlank(filename)) {
            log.error("ChatTtsClient error: filename is empty");
            throw new ServerException("ChatTtsClient error: filename is empty");
        }
        return filename;
    }


    private void mediaPlay(String localFilePath) {
        mediaPlayerComponent.mediaPlayer().media().play(localFilePath);
    }

    // private void exit(int result) {
    //     // It is not allowed to call back into LibVLC from an event handling thread, so submit() is used
    //     mediaPlayerComponent.mediaPlayer().submit(() -> {
    //         mediaPlayerComponent.mediaPlayer().release();
    //         System.exit(result);
    //     });
    // }

}
