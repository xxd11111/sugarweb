package com.sugarcoat.param;

import com.sugarcoat.param.application.DefaultParameterServiceImpl;
import com.sugarcoat.param.application.ParameterService;
import com.sugarcoat.param.domain.SugarcoatParameterRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 参数自动配置
 *
 * @author xxd
 * @date 2023/5/7 0:00
 */
@Configuration
public class ParameterAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ParameterService getParamService(SugarcoatParameterRepository sugarcoatParameterRepository) {
        return new DefaultParameterServiceImpl(sugarcoatParameterRepository);
    }
}
