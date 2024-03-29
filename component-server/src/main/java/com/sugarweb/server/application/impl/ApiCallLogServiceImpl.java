package com.sugarweb.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.server.domain.QApiCallLog;
import com.sugarweb.server.application.ApiCallLogService;
import com.sugarweb.server.domain.ApiCallLog;
import com.sugarweb.server.application.dto.ApiCallLogQueryDto;
import com.sugarweb.server.domain.ApiCallLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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

	@Override
	public ApiCallLog findOne(String id) {
		return sgcApiCallLogRepository.findById(id).orElseThrow(() -> new ValidateException("访问日志不存在"));
	}

	@Override
	public PageData<ApiCallLog> findPage(PageQuery pageDto, ApiCallLogQueryDto apiCallLogQueryDto) {
		QApiCallLog qApiCallLog = QApiCallLog.apiCallLog;
		PageRequest pageRequest = PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, qApiCallLog.requestTime.getMetadata().getName());
		Page<ApiCallLog> page = sgcApiCallLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
