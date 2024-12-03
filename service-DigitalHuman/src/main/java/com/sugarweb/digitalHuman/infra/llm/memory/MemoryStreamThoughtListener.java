package com.sugarweb.digitalHuman.infra.llm.memory;

import com.sugarweb.digitalHuman.domain.StagePerformanceMsg;
import com.sugarweb.digitalHuman.infra.llm.thought.StreamThoughtListener;
import com.sugarweb.digitalHuman.infra.llm.thought.ThoughtContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * StreamTokenSpeakAdapt
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class MemoryStreamThoughtListener implements StreamThoughtListener {

    private final PerformanceMemoryComponent performanceMemoryComponent;

    public MemoryStreamThoughtListener(PerformanceMemoryComponent performanceMemoryComponent) {
        this.performanceMemoryComponent = performanceMemoryComponent;
    }

    @Override
    public void onNext(ThoughtContext thoughtContext, String token) {

    }

    @Override
    public void onComplete(ThoughtContext thoughtContext) {
        StagePerformanceMsg stagePerformanceMsg = thoughtContext.getCurrentMsg();
        stagePerformanceMsg.setEndTime(LocalDateTime.now());
        String assistantMsg = thoughtContext.getAssistantMsg();
        stagePerformanceMsg.setAnswer(assistantMsg);
        performanceMemoryComponent.memorize(stagePerformanceMsg);
    }

    @Override
    public void onError(ThoughtContext thoughtContext, Throwable error) {

    }
}
