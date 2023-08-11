package com.sugarcoat.support.dict;

import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.support.dict.application.DictionaryService;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryManager;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryGroupRepository;
import com.sugarcoat.support.dict.domain.SugarcoatDictionaryRepository;
import com.sugarcoat.support.dict.application.impl.SugarcoatDictionaryServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@EntityScan
@EnableJpaRepositories
@Configuration(proxyBeanMethods = false)
public class DictionaryAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DictionaryService dictService(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository,
			SugarcoatDictionaryRepository sugarcoatDictionaryRepository) {
		return new SugarcoatDictionaryServiceImpl(sugarcoatDictionaryGroupRepository, sugarcoatDictionaryRepository);
	}

	@Bean
	@ConditionalOnMissingBean
	public DictionaryManager dictionaryClient(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository) {
		return new SugarcoatDictionaryManager(sugarcoatDictionaryGroupRepository);
	}

}
