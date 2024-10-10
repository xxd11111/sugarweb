package com.sugarweb.chatAssistant.temp.ability;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.infra.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class SpeakAbility {

    private boolean isSpeaking = false;

    private final BlockingQueue<String> speakQueue = new LinkedBlockingQueue<>();

    public void speak() {
        try {
            String take = speakQueue.take();
            VlcUtil.playAudio(take);
        } catch (InterruptedException e) {
            log.error("SpeakAbility.speak InterruptedException", e);
        }
    }


    private StringBuffer sb = new StringBuffer();

    public void streamSubmit() {
        String string = sb.toString();
        if (StrUtil.isNotEmpty(string)) {
            addSpeakContent(string);
        } else {
            sb = new StringBuffer();
        }
    }

    public void addSpeakStreamContent(String token) {
        sb.append(token);
    }

    public void addSpeakContent(String content) {
        if (StrUtil.isBlank(content)) {
            return;
        }
        ChatTtsClient chatTtsClient = new ChatTtsClient();
        TtsResponse tts = chatTtsClient.tts(TtsRequest.builder()
                .text(content)
                .build());
        if (!tts.success()) {
            log.error("ChatTtsClient error: {}", tts.getMsg());
            return;
        }
        List<TtsAudioFile> audioFiles = tts.getAudio_files();
        if (audioFiles.isEmpty()) {
            log.error("ChatTtsClient error: audioFiles is empty");
            return;
        }
        TtsAudioFile first = audioFiles.getFirst();
        String filename = first.getFilename();
        if (StrUtil.isBlank(filename)) {
            log.error("ChatTtsClient error: filename is empty");
            return;
        }
        try {
            speakQueue.put(filename);
        } catch (InterruptedException e) {
            log.error("SpeakAbility.addSpeakContent InterruptedException", e);
        }
    }

}
