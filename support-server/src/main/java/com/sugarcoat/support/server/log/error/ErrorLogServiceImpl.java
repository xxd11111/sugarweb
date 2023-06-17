package com.sugarcoat.support.server.log.error;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import com.sugarcoat.api.exception.ValidateException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 异常日志服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/1
 */
@AllArgsConstructor
public class ErrorLogServiceImpl implements ErrorLogService {

	private final ErrorLogRepository errorLogRepository;

	@Override
	public ErrorLog findOne(String id) {
		return errorLogRepository.findById(id).orElseThrow(() -> new ValidateException("异常日志不存在,id:{}", id));
	}

	@Override
	public PageData<ErrorLog> findPage(PageParameter pageParameter, ErrorLogQueryCmd errorLogQueryCmd) {
		QErrorLog qErrorLog = QErrorLog.errorLog;
		PageRequest pageRequest = PageRequest.of(pageParameter.getPageNum(), pageParameter.getPageSize())
				.withSort(Sort.Direction.DESC, qErrorLog.errorTime.getMetadata().getName());
		Page<ErrorLog> page = errorLogRepository.findAll(Expressions.TRUE, pageRequest);
		return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
	}

}
