package com.sugarweb.digitalHuman.infra.llm.memory;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.StagePerformanceMsg;

/**
 * ChatMemoryComponent
 * 聊天记忆组件
 *
 * @author xxd
 * @version 1.0
 */
public class PerformanceMemoryComponent {

    public StagePerformanceMsg lastPerformanceMsg(String performanceId) {
        return Db.lambdaQuery(StagePerformanceMsg.class)
                .eq(StagePerformanceMsg::getPerformanceId, performanceId)
                .orderByDesc(StagePerformanceMsg::getCreateTime)
                .last("limit 1")
                .one();
    }

    public StagePerformanceMsg lastUserMsg(String performanceId, String userId) {
        return Db.lambdaQuery(StagePerformanceMsg.class)
                .eq(StagePerformanceMsg::getPerformanceId, performanceId)
                .eq(StagePerformanceMsg::getUserId, userId)
                .orderByDesc(StagePerformanceMsg::getCreateTime)
                .last("limit 1")
                .one();
    }

    public void memorize(StagePerformanceMsg performanceMsg) {
        Db.saveOrUpdate(performanceMsg);
    }

}
