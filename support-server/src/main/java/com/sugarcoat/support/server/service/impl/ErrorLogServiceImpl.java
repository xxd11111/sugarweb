package com.sugarcoat.support.server.service.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.server.domain.QSgcApiCallLog;
import com.sugarcoat.support.server.domain.QSgcApiErrorLog;
import com.sugarcoat.support.server.service.ErrorLogService;
import com.sugarcoat.support.server.domain.SgcApiErrorLog;
import com.sugarcoat.support.server.service.dto.ErrorLogQueryDto;
import com.sugarcoat.support.server.domain.ApiErrorLogRepository;
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
 * @since 2023/6/1
 */
@AllArgsConstructor
@Component
public class ErrorLogServiceImpl implements ErrorLogService {

	private final ApiErrorLogRepository apiErrorLogRepository;

	@Override
	public SgcApiErrorLog findOne(String id) {
		return apiErrorLogRepository.findById(id).orElseThrow(() -> new ValidateException("异常日志不存在,id:{}", id));
	}

	@Override
	public PageData<SgcApiErrorLog> findPage(PageDto pageDto, ErrorLogQueryDto queryDto) {
		QSgcApiErrorLog apiErrorLog = QSgcApiErrorLog.sgcApiErrorLog;
		PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
				.withSort(Sort.Direction.DESC, apiErrorLog.getMetadata().getName());
		Page<SgcApiErrorLog> page = apiErrorLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}