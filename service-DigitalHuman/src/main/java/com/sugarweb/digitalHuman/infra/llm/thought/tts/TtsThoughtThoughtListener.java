package com.sugarweb.digitalHuman.infra.llm.thought.tts;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContent;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamThoughtListener;
import com.sugarweb.digitalHuman.infra.llm.thought.ThoughtContext;
import lombok.extern.slf4j.Slf4j;

/**
 * StreamTokenSpeakAdapt
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class TtsThoughtThoughtListener implements StreamThoughtListener {

    private final OutputContainer outputContainer;
    private final StringBuilder sb = new StringBuilder();
    private int currentSplitId = 0;

    public TtsThoughtThoughtListener(OutputContainer outputContainer) {
        this.outputContainer = outputContainer;
    }

    @Override
    public void onNext(ThoughtContext thoughtContext, String token) {
        if (StrUtil.isBlank(token)) {
            return;
        }
        if (sb.length() + token.length() > 20) {
            handleTokenWithPunctuation(token, thoughtContext.getThoughtId());
        } else {
            sb.append(token);
        }
    }

    private void handleTokenWithPunctuation(String token, long thinkId) {
        String[] punctuationMarks = {"。", "！", "!", "？", "?"};
        for (String mark : punctuationMarks) {
            if (token.contains(mark)) {
                String[] split = token.split(mark, 2); // 限制分割次数为2，避免创建过多数组
                if (split.length > 1) {
                    sb.append(split[0]).append(mark);
                    addSpeakContent(thinkId, currentSplitId++, sb.toString());
                    sb.setLength(0); // 清空StringBuilder
                    sb.append(split[1]);
                    return;
                }
                // 只分割一次
                break;
            }
        }
        sb.append(token);
    }

    private void addSpeakContent(long thinkId, int spiltId, String content) {
        if (StrUtil.isBlank(content)) {
            return;
        }
        outputContainer.offer(OutputContent.builder()
                .thinkId(thinkId)
                .splitId(spiltId)
                .content(content)
                .build());
    }

    @Override
    public void onComplete(ThoughtContext thoughtContext) {
        addSpeakContent(thoughtContext.getThoughtId(), currentSplitId++, sb.toString());
        sb.setLength(0);
        currentSplitId = 0;
    }

    @Override
    public void onError(ThoughtContext thoughtContext, Throwable error) {
        sb.setLength(0);
        currentSplitId = 0;
    }


}
