package com.sugarweb.server.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.server.domain.ApiErrorLog;
import com.sugarweb.server.application.ApiErrorLogService;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;
import com.sugarweb.server.domain.ApiErrorLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 异常日志服务
 *
 * @author xxd
 * @version 1.0
 */
@AllArgsConstructor
@Component
public class ApiApiErrorLogServiceImpl implements ApiErrorLogService {

	private final ApiErrorLogRepository sgcApiErrorLogRepository;

	
	public ApiErrorLog findOne(String id) {
		return Optional.ofNullable(sgcApiErrorLogRepository.selectById(id)).orElseThrow(() -> new ValidateException("异常日志不存在,id:{}", id));
	}

	
	public IPage<ApiErrorLog> findPage(PageQuery pageDto, ApiErrorLogQueryDto queryDto) {
		Page<ApiErrorLog> page = sgcApiErrorLogRepository.selectPage(new Page<>(pageDto.getPageNumber(), pageDto.getPageSize()), new LambdaQueryWrapper<>());
		return page;
	}

}
