package com.sugarweb.chatAssistant.temp.ability;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.infra.ChatTtsClient;
import com.sugarweb.chatAssistant.infra.TtsAudioFile;
import com.sugarweb.chatAssistant.infra.TtsRequest;
import com.sugarweb.chatAssistant.infra.TtsResponse;
import com.sugarweb.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
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
    public final BlockingQueue<MediaContent> playMediaQueue = new PriorityBlockingQueue<>(100,
            Comparator.comparingLong(MediaContent::getChatId)
                    .thenComparingInt(MediaContent::getSplitId)
                    .thenComparing((o1, o2) -> 1));

    private final ExecutorService executor;

    private Thread speakThread = null;

    private AudioPlayerComponent mediaPlayerComponent = null;

    private final ReentrantLock speakingLock = new ReentrantLock();

    private long currentChatId = 0;

    private long currentSpiltId = 0;

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
        //todo 存在播放时序问题，解决插队，响应问题
        if (speakThread != null) {
            speakThread = Thread.ofVirtual().start(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        MediaContent take = playMediaQueue.take();
                        if (take.getChatId() > currentChatId) {
                            playMediaQueue.put(take);
                        }
                        speakingLock.lock();
                        Future<String> filePathFuture = take.getFilePath();
                        String filePath = filePathFuture.get();
                        //注意此方法是异步执行，调用vlc播放
                        mediaPlayerComponent.mediaPlayer().media().play(filePath);
                    } catch (InterruptedException e) {
                        speakingLock.unlock();
                        log.error("SpeakAbility.start InterruptedException", e);
                    } catch (ExecutionException e) {
                        speakingLock.unlock();
                        log.error("SpeakAbility.start ExecutionException", e);
                    }
                }
            });
        }
    }

    public void shutdown() {
        if (speakThread != null) {
            speakThread.interrupt();
        }
    }

    public void cancel(long chatId) {

    }

    public void addSpeakContent(long chatId, int spiltId, String content) {
        if (currentChatId == 0) {
            currentChatId = chatId;
            currentSpiltId = spiltId;
        }
        Future<String> submit = executor.submit(() -> tts(content));
        playMediaQueue.add(MediaContent.builder()
                .chatId(chatId)
                .splitId(spiltId)
                .content(content)
                .filePath(submit)
                .build());
    }

    public void chatContentFinish(long chatId) {

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


    private void start(String mrl) {
        mediaPlayerComponent.mediaPlayer().media().play(mrl);
    }

    private void exit(int result) {
        // It is not allowed to call back into LibVLC from an event handling thread, so submit() is used
        mediaPlayerComponent.mediaPlayer().submit(() -> {
            mediaPlayerComponent.mediaPlayer().release();
            System.exit(result);
        });
    }

}
