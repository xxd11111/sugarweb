package com.sugarcoat.support.server.service.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.server.domain.QSgcApiErrorLog;
import com.sugarcoat.support.server.service.ApiErrorLogService;
import com.sugarcoat.support.server.domain.SgcApiErrorLog;
import com.sugarcoat.support.server.service.dto.ApiErrorLogQueryDto;
import com.sugarcoat.support.server.domain.SgcApiErrorLogRepository;
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
public class ApiApiErrorLogServiceImpl implements ApiErrorLogService {

	private final SgcApiErrorLogRepository sgcApiErrorLogRepository;

	@Override
	public SgcApiErrorLog findOne(String id) {
		return sgcApiErrorLogRepository.findById(id).orElseThrow(() -> new ValidateException("异常日志不存在,id:{}", id));
	}

	@Override
	public PageData<SgcApiErrorLog> findPage(PageDto pageDto, ApiErrorLogQueryDto queryDto) {
		QSgcApiErrorLog apiErrorLog = QSgcApiErrorLog.sgcApiErrorLog;
		PageRequest pageRequest = PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, apiErrorLog.getMetadata().getName());
		Page<SgcApiErrorLog> page = sgcApiErrorLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
