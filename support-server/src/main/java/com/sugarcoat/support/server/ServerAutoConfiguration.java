package com.sugarcoat.support.server;

import com.sugarcoat.support.server.controller.ApiController;
import com.sugarcoat.support.server.domain.SgcApiRepository;
import com.sugarcoat.support.server.service.SgcApiService;
import com.sugarcoat.support.server.service.impl.SgcApiServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 服务自动配置
 *
 * @author xxd
 * @since 2023/5/8 23:28
 */
@Configuration(proxyBeanMethods = false)
@EntityScan
@EnableJpaRepositories
public class ServerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public SgcApiService serverApiService(SgcApiRepository sgcApiRepository) {
		return new SgcApiServiceImpl(sgcApiRepository);
	}

	@Bean
	public ApiController serverApiController(SgcApiService sgcApiService){
		return new ApiController(sgcApiService);
	}

	// @Bean
	// public GlobalExceptionHandler globalExceptionHandler(){
	// 	return new GlobalExceptionHandler();
	// }

}
