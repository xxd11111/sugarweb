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
 * TODO 环境上下文功能定位有点问题，当前用stageInfo完全可以解决；
 * 主要关注点应该是stage运行时产生的额外相关信息
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
     * 线程池
     */
    private ExecutorService executor;

    private StageInfo stageInfo;

    private AgentInfo agentInfo;

    private SceneInfo sceneInfo;

    private PerformanceInfo performanceInfo;

}
