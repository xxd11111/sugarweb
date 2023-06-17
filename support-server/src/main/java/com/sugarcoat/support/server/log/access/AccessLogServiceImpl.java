package com.sugarcoat.support.server.log.access;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import com.sugarcoat.api.exception.ValidateException;
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
	public PageData<AccessLog> findPage(PageParameter pageParameter, AccessLogQueryCmd accessLogQueryCmd) {
		QAccessLog qAccessLog = QAccessLog.accessLog;
		PageRequest pageRequest = PageRequest.of(pageParameter.getPageNum(), pageParameter.getPageSize())
				.withSort(Sort.Direction.DESC, qAccessLog.beginTime.getMetadata().getName());
		Page<AccessLog> page = accessLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
