package com.xxd.sugarcoat.support.param;

import com.xxd.sugarcoat.support.param.api.ParamService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxd
 * @description TODO
 * @date 2023/5/7 0:00
 */
@Configuration
public class ParamAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ParamService getParamService(ParamRepository paramRepository){
        return new DefaultParamServiceImpl(paramRepository);
    }
}
