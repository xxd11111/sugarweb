package com.sugarweb.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.exception.ValidateException;
import com.sugarweb.server.domain.ApiErrorLog;
import com.sugarweb.server.domain.QApiErrorLog;
import com.sugarweb.server.application.ApiErrorLogService;
import com.sugarweb.server.application.dto.ApiErrorLogQueryDto;
import com.sugarweb.server.domain.ApiErrorLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
	public PageData<ApiErrorLog> findPage(PageRequest pageDto, ApiErrorLogQueryDto queryDto) {
		QApiErrorLog apiErrorLog = QApiErrorLog.apiErrorLog;
		org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, apiErrorLog.getMetadata().getName());
		Page<ApiErrorLog> page = sgcApiErrorLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
