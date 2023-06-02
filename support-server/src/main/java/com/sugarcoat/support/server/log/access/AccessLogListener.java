package com.sugarcoat.support.server.log.access;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;

/**
 * 访问日志监听者
 *
 * @author xxd
 * @date 2022-11-21
 */
@Async
@AllArgsConstructor
public class AccessLogListener implements ApplicationListener<AccessLogEvent> {

    private final AccessLogRepository accessLogRepository;

    @Override
    public void onApplicationEvent(@NonNull AccessLogEvent event) {
        AccessLog source = (AccessLog) event.getSource();
        accessLogRepository.save(source);
    }
}
