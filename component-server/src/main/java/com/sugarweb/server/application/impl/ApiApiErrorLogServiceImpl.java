package com.sugarweb.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.server.domain.ApiErrorLog;
import com.sugarweb.server.domain.QApiErrorLog;
import com.sugarweb.server.application.ApiErrorLogService;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;
import com.sugarweb.server.domain.ApiErrorLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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

	@Override
	public ApiErrorLog findOne(String id) {
		return sgcApiErrorLogRepository.findById(id).orElseThrow(() -> new ValidateException("异常日志不存在,id:{}", id));
	}

	@Override
	public PageData<ApiErrorLog> findPage(PageQuery pageDto, ApiErrorLogQueryDto queryDto) {
		QApiErrorLog apiErrorLog = QApiErrorLog.apiErrorLog;
		PageRequest pageRequest = PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, apiErrorLog.getMetadata().getName());
		Page<ApiErrorLog> page = sgcApiErrorLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
