package com.sugarweb.digitalHuman.component.llm.memory;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.entity.StagePerformanceMsg;

/**
 * ChatMemoryComponent
 * 聊天记忆组件
 *
 * @author xxd
 * @version 1.0
 */
public class PerformanceMemoryComponent {

    public StagePerformanceMsg loadMemory(String performanceId, String userId) {
        return Db.lambdaQuery(StagePerformanceMsg.class)
                .eq(StagePerformanceMsg::getPerformanceId, performanceId)
                .eq(StrUtil.isNotEmpty(userId), StagePerformanceMsg::getUserId, userId)
                .orderByDesc(StagePerformanceMsg::getCreateTime)
                .last("limit 1")
                .one();
    }

    public void memorize(StagePerformanceMsg performanceMsg) {
        Db.saveOrUpdate(performanceMsg);
    }

}
