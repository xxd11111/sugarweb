package com.sugarcoat.support.param;

import com.sugarcoat.support.param.application.DefaultParameterServiceImpl;
import com.sugarcoat.support.param.application.ParameterService;
import com.sugarcoat.support.param.domain.SugarcoatParameterRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 参数自动配置
 *
 * @author xxd
 * @since 2023/5/7 0:00
 */
@Configuration
@EnableJpaRepositories
@EntityScan
public class ParameterAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ParameterService getParamService(SugarcoatParameterRepository sugarcoatParameterRepository) {
		return new DefaultParameterServiceImpl(sugarcoatParameterRepository);
	}

}
