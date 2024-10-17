package com.sugarweb.chatAssistant.agent.ability.adaptor;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.chatAssistant.agent.ability.output.speak.SpeakContent;
import com.sugarweb.chatAssistant.infra.tts.ChatTtsClient;
import com.sugarweb.chatAssistant.infra.tts.TtsAudioFile;
import com.sugarweb.chatAssistant.infra.tts.TtsRequest;
import com.sugarweb.chatAssistant.infra.tts.TtsResponse;
import com.sugarweb.framework.exception.ServerException;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.output.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * StreamTokenSpeakAdapt
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class ThinkOutputAdaptor {
    public ThinkOutputAdaptor(ExecutorService executor) {
        this.executor = executor;
    }

    private final ExecutorService executor;

    private final BlockingQueue<SpeakContent> thinkOutputList = new LinkedBlockingQueue<>();

    public StreamingResponseHandler<AiMessage> streamingOutputHandler(long thinkId) {
        return new StreamingResponseHandler<AiMessage>() {
            private int currentSplitId = 0;
            private final StringBuilder sb = new StringBuilder();

            public void onNext(String token) {
                if (StrUtil.isBlank(token)) {
                    return;
                }
                if (sb.length() + token.length() > 10) {
                    //判断token是否有中断符号 '。' 有的话截断
                    if (token.contains("。")) {
                        String[] split = token.split("。");
                        sb.append(split[0]);
                        addSpeakContent(thinkId, currentSplitId++, sb.toString());
                        String last = split[split.length - 1];
                        sb.append(last);
                    } else {
                        sb.append(token);
                    }
                } else {
                    sb.append(token);
                }
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                String content = sb.toString();
                if (StrUtil.isNotEmpty(content)) {
                    addSpeakContent(thinkId, currentSplitId, content);
                }
            }

            @Override
            public void onError(Throwable error) {
                addSpeakContent(thinkId, currentSplitId, "大脑宕机了。");
            }
        };
    }


    public void addSpeakContent(long chatId, int spiltId, String content) {
        Future<String> submit = executor.submit(() -> tts(content));
        try {
            thinkOutputList.put(SpeakContent.builder()
                    .chatId(chatId)
                    .splitId(spiltId)
                    .content(content)
                    .filePath(submit)
                    .build());
        } catch (InterruptedException e) {
            log.error("addSpeakContent InterruptedException", e);
            Thread.currentThread().interrupt();
        }
    }

    public SpeakContent take() throws InterruptedException {
        return thinkOutputList.take();
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
