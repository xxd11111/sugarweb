package com.sugarweb.digitalHuman.infra.llm;

import com.sugarweb.digitalHuman.domain.*;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;

/**
 * 主要关注点应该是stage运行时产生的额外相关信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StageContext {

    private ExecutorService executor;

    private Stage stage;

    private StagePerformance stagePerformance;

    private Script script;

    private Actor actor;

    private Dataset dataset;

    private StreamingChatLanguageModel chatLanguageModel;

}
