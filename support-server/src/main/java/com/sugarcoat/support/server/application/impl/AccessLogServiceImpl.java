package com.sugarcoat.support.server.application.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.api.exception.ValidateException;
import com.sugarcoat.support.server.application.AccessLogService;
import com.sugarcoat.support.server.domain.access.AccessLog;
import com.sugarcoat.support.server.application.dto.AccessLogQueryDto;
import com.sugarcoat.support.server.domain.access.AccessLogRepository;
import com.sugarcoat.support.server.domain.access.QAccessLog;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * 访问日志服务
 *
 * @author xxd
 * @date 2022-11-21
 */
@Component
@AllArgsConstructor
public class AccessLogServiceImpl implements AccessLogService {

	private final AccessLogRepository accessLogRepository;

	@Override
	public AccessLog findOne(String id) {
		return accessLogRepository.findById(id).orElseThrow(() -> new ValidateException("访问日志不存在"));
	}

	@Override
	public PageData<AccessLog> findPage(PageDto pageDto, AccessLogQueryDto accessLogQueryDto) {
		QAccessLog qAccessLog = QAccessLog.accessLog;
		PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
				.withSort(Sort.Direction.DESC, qAccessLog.beginTime.getMetadata().getName());
		Page<AccessLog> page = accessLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
