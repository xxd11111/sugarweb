package com.xxd.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.exception.ValidateException;
import com.xxd.server.domain.QSgcApiCallLog;
import com.xxd.server.application.ApiCallLogService;
import com.xxd.server.domain.SgcApiCallLog;
import com.xxd.server.application.dto.AccessLogQueryDto;
import com.xxd.server.domain.SgcApiCallLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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

	private final SgcApiCallLogRepository sgcApiCallLogRepository;

	@Override
	public SgcApiCallLog findOne(String id) {
		return sgcApiCallLogRepository.findById(id).orElseThrow(() -> new ValidateException("访问日志不存在"));
	}

	@Override
	public PageData<SgcApiCallLog> findPage(PageRequest pageDto, AccessLogQueryDto accessLogQueryDto) {
		QSgcApiCallLog qSgcApiCallLog = QSgcApiCallLog.sgcApiCallLog;
		org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, qSgcApiCallLog.requestTime.getMetadata().getName());
		Page<SgcApiCallLog> page = sgcApiCallLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
