package com.sugarweb.server.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.server.application.ApiCallLogService;
import com.sugarweb.server.domain.ApiCallLog;
import com.sugarweb.server.application.dto.ApiCallLogQueryDto;
import com.sugarweb.server.domain.ApiCallLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 访问日志服务
 *
 * @author xxd
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class ApiCallLogServiceImpl implements ApiCallLogService {

	private final ApiCallLogRepository sgcApiCallLogRepository;

	
	public ApiCallLog findOne(String id) {
		return Optional.ofNullable(sgcApiCallLogRepository.selectById(id)).orElseThrow(() -> new ValidateException("访问日志不存在"));
	}

	
	public IPage<ApiCallLog> findPage(PageQuery pageQuery, ApiCallLogQueryDto apiCallLogQueryDto) {
		return sgcApiCallLogRepository.selectPage(new Page<ApiCallLog>(pageQuery.getPageNumber(), pageQuery.getPageSize()), new LambdaQueryWrapper<>());
	}

}
