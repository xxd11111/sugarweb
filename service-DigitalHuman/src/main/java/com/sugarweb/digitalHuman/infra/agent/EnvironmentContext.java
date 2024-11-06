package com.sugarweb.digitalHuman.infra.agent;

import com.sugarweb.digitalHuman.domain.AgentInfo;
import com.sugarweb.digitalHuman.domain.PerformanceInfo;
import com.sugarweb.digitalHuman.domain.SceneInfo;
import com.sugarweb.digitalHuman.domain.StageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnvironmentContext {

    /**
     * 线程池 这个感觉有点怪，但是暂时先这样吧
     */
    private ExecutorService executor;

    private StageInfo stageInfo;

    private AgentInfo agentInfo;

    private SceneInfo sceneInfo;

    private PerformanceInfo performanceInfo;

}
