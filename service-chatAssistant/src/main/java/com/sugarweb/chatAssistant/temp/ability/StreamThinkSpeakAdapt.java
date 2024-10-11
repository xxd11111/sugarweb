package com.sugarweb.chatAssistant.temp.ability;

import cn.hutool.core.util.StrUtil;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.output.Response;

/**
 * StreamTokenSpeakAdapt
 *
 * @author xxd
 * @version 1.0
 */
public class StreamThinkSpeakAdapt implements StreamingResponseHandler<AiMessage> {

    private int currentSplitId = 1;

    private final long chatId;

    private final StringBuilder sb = new StringBuilder();

    private final SpeakAbility speakAbility;

    public StreamThinkSpeakAdapt(long chatId, SpeakAbility speakAbility) {
        this.chatId = chatId;
        this.speakAbility = speakAbility;
    }

    @Override
    public void onNext(String token) {
        addSpeakStreamContent(token);
    }

    @Override
    public void onComplete(Response<AiMessage> response) {
        StreamingResponseHandler.super.onComplete(response);
        streamFinish();
    }

    @Override
    public void onError(Throwable error) {
        streamCancel();
    }

    private void streamFinish() {
        String content = sb.toString();
        if (StrUtil.isNotEmpty(content)) {
            speakAbility.addSpeakContent(chatId, currentSplitId, content);
        }
    }

    private void streamCancel() {
        speakAbility.cancel(chatId);
    }

    private void addSpeakStreamContent(String token) {
        if (StrUtil.isBlank(token)) {
            return;
        }
        if (sb.length() + token.length() > 10) {
            //判断token是否有中断符号 '。' 有的话截断
            if (token.contains("。")) {
                String[] split = token.split("。");
                sb.append(split[0]);
                speakAbility.addSpeakContent(chatId, currentSplitId++, sb.toString());
                String last = split[split.length - 1];
                sb.append(last);
            } else {
                sb.append(token);
            }
        } else {
            sb.append(token);
        }
    }

}
