package com.sugarcoat.support.server;

import com.sugarcoat.support.server.controller.ServerApiController;
import com.sugarcoat.support.server.domain.ServerApiRepository;
import com.sugarcoat.support.server.service.ServerApiService;
import com.sugarcoat.support.server.service.impl.ServerApiServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationEventPublisher;
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
	public ServerApiService serverApiService(ServerApiRepository serverApiRepository) {
		return new ServerApiServiceImpl(serverApiRepository);
	}

	@Bean
	public ServerApiController serverApiController(ServerApiService serverApiService){
		return new ServerApiController(serverApiService);
	}

}
