package com.sugarcoat.support.server;

import com.sugarcoat.support.server.serverApi.ServerApiRepository;
import com.sugarcoat.support.server.serverApi.ServerApiService;
import com.sugarcoat.support.server.serverApi.ServerApiServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动配置
 *
 * @author xxd
 * @date 2023/5/8 23:28
 */
@Configuration(proxyBeanMethods = false)
@EntityScan
@ComponentScan
public class ServerAutoConfiguration {

	@Bean
//	@ConditionalOnMissingBean
	public ServerApiService serverApiService(ServerApiRepository serverApiRepository) {
		return new ServerApiServiceImpl(serverApiRepository);
	}

}
