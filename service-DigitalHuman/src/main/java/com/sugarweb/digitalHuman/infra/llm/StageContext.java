package com.sugarweb.digitalHuman.infra.llm;

import com.sugarweb.digitalHuman.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主要关注点应该是stage运行时产生的数据信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StageContext {

    private Stage stage;

    private Script script;

    private Actor actor;

    private StagePerformance stagePerformance;

    private Dataset dataset;


}
