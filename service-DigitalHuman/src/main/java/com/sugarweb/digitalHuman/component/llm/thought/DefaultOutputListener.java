package com.sugarweb.digitalHuman.component.llm.thought;

import com.sugarweb.digitalHuman.component.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.component.llm.output.OutputContent;
import lombok.extern.slf4j.Slf4j;

/**
 * TtsThoughtListener
 * 非线程安全
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class DefaultOutputListener implements StreamThoughtListener {

    private final OutputContainer outputContainer;
    private final StringBuilder sb = new StringBuilder();
    private int currentSplitId = 0;

    public DefaultOutputListener(OutputContainer outputContainer) {
        this.outputContainer = outputContainer;
    }

    @Override
    public void onNext(ThoughtRequest thoughtRequest, String token) {
        sb.append(token);
        OutputContent outputContent = OutputContent.builder()
                .content(sb.toString())
                .thoughtId(thoughtRequest.getThoughtId())
                .splitId(currentSplitId++)
                .build();
        outputContainer.add(outputContent);
    }

    @Override
    public void onComplete(ThoughtRequest thoughtRequest) {
        sb.setLength(0);
        currentSplitId = 0;
    }

    @Override
    public void onError(ThoughtRequest thoughtRequest, Throwable error) {
        sb.setLength(0);
        currentSplitId = 0;
    }


}
