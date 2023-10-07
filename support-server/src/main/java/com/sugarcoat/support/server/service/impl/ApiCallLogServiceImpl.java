package com.sugarcoat.support.server.service.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.server.domain.QSgcApiCallLog;
import com.sugarcoat.support.server.service.ApiCallLogService;
import com.sugarcoat.support.server.domain.SgcApiCallLog;
import com.sugarcoat.support.server.service.dto.AccessLogQueryDto;
import com.sugarcoat.support.server.domain.SgcApiCallLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * 访问日志服务
 *
 * @author xxd
 * @since 2022-11-21
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
	public PageData<SgcApiCallLog> findPage(PageDto pageDto, AccessLogQueryDto accessLogQueryDto) {
		QSgcApiCallLog qSgcApiCallLog = QSgcApiCallLog.sgcApiCallLog;
		PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
				.withSort(Sort.Direction.DESC, qSgcApiCallLog.callDate.getMetadata().getName());
		Page<SgcApiCallLog> page = sgcApiCallLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
