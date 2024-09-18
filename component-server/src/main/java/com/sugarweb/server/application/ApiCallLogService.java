package com.sugarweb.server.application;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.server.application.dto.ApiCallLogQuery;
import com.sugarweb.server.domain.ApiCallLog;

/**
 * 访问日志服务
 *
 * @author xxd
 * @version 1.0
 */
public class ApiCallLogService {

    public ApiCallLog getOne(String id) {
        return Db.getById(id, ApiCallLog.class);
    }

    public IPage<ApiCallLog> page(PageQuery pageQuery, ApiCallLogQuery query) {
        return Db.page(PageHelper.getPage(pageQuery), new LambdaQueryWrapper<ApiCallLog>()
                .like(StrUtil.isNotEmpty(query.getApiName()), ApiCallLog::getApiName, query.getApiName())
                .like(StrUtil.isNotEmpty(query.getUsername()), ApiCallLog::getUsername, query.getUsername())
                .like(StrUtil.isNotEmpty(query.getRequestIp()), ApiCallLog::getRequestIp, query.getRequestIp())
        );
    }

}
