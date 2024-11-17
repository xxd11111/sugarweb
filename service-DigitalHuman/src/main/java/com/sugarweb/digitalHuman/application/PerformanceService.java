package com.sugarweb.digitalHuman.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.StagePerformance;
import org.springframework.stereotype.Service;

/**
 * 表演服务
 */
@Service
public class PerformanceService {

    public StagePerformance getById(String performanceId) {
       return Db.getById(performanceId, StagePerformance.class);
    }

}
