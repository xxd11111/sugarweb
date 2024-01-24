package com.xxd.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.exception.ValidateException;
import com.xxd.server.domain.SgcApiErrorLog;
import com.xxd.server.domain.QSgcApiErrorLog;
import com.xxd.server.application.ApiErrorLogService;
import com.xxd.server.application.dto.ApiErrorLogQueryDto;
import com.xxd.server.domain.SgcApiErrorLogRepository;
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

	private final SgcApiErrorLogRepository sgcApiErrorLogRepository;

	@Override
	public SgcApiErrorLog findOne(String id) {
		return sgcApiErrorLogRepository.findById(id).orElseThrow(() -> new ValidateException("异常日志不存在,id:{}", id));
	}

	@Override
	public PageData<SgcApiErrorLog> findPage(PageRequest pageDto, ApiErrorLogQueryDto queryDto) {
		QSgcApiErrorLog apiErrorLog = QSgcApiErrorLog.sgcApiErrorLog;
		org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, apiErrorLog.getMetadata().getName());
		Page<SgcApiErrorLog> page = sgcApiErrorLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
