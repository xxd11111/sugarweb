package com.sugarcoat.support.server.log;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;

/**
 * 异常日志监听者
 *
 * @author xxd
 * @version 1.0
 * @date 2023/6/1
 */
@Async
@AllArgsConstructor
public class ErrorLogListener implements ApplicationListener<ErrorLogEvent> {

    private final ErrorLogRepository errorLogRepository;

    @Override
    public void onApplicationEvent(@NonNull ErrorLogEvent event) {
        ErrorLog source = (ErrorLog) event.getSource();
        errorLogRepository.save(source);
    }
}
