package com.sugarweb.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.exception.ValidateException;
import com.sugarweb.server.domain.QSgcApiCallLog;
import com.sugarweb.server.application.ApiCallLogService;
import com.sugarweb.server.domain.SgcApiCallLog;
import com.sugarweb.server.application.dto.AccessLogQueryDto;
import com.sugarweb.server.domain.BaseApiCallLogRepository;
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

	private final BaseApiCallLogRepository sgcApiCallLogRepository;

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
