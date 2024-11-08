package com.sugarweb.digitalHuman.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.digitalHuman.domain.PerformanceInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表演服务
 */
@Service
public class PerformanceService {

    public PerformanceInfo getById(String performanceId) {
       return Db.getById(performanceId, PerformanceInfo.class);
    }

}
