package com.sugarcoat.support.server.log.access;

import com.querydsl.core.types.dsl.Expressions;
import com.sugarcoat.protocol.PageData;
import com.sugarcoat.protocol.PageParameter;
import com.sugarcoat.protocol.exception.ValidateException;
import com.sugarcoat.support.server.log.QErrorLog;
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
        QErrorLog qErrorLog = QErrorLog.errorLog;
        PageRequest pageRequest = PageRequest.of(pageParameter.getPageNum(), pageParameter.getPageSize())
                .withSort(Sort.Direction.DESC, qErrorLog.errorTime.getMetadata().getName());
        Page<AccessLog> page = accessLogRepository.findAll(Expressions.TRUE, pageRequest);
        return new PageData<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
    }
}
