package com.sugarcoat.support.server;

import com.sugarcoat.support.server.serverApi.ServerApiRepository;
import com.sugarcoat.support.server.serverApi.ServerApiService;
import com.sugarcoat.support.server.serverApi.ServerApiServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxd
 * @description TODO
 * @date 2023/5/8 23:28
 */
@Configuration(proxyBeanMethods = false)
public class ServerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public ServerApiService serverApiService(ServerApiRepository serverApiRepository) {
		return new ServerApiServiceImpl(serverApiRepository);
	}

}
