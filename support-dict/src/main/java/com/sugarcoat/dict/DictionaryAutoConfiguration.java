package com.sugarcoat.dict;

import com.sugarcoat.dict.application.DictionaryService;
import com.sugarcoat.dict.domain.SugarcoatDictionaryGroupRepository;
import com.sugarcoat.dict.domain.SugarcoatDictionaryRepository;
import com.sugarcoat.dict.application.impl.SugarcoatDictionaryServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xxd
 * @description 字典工具类初始化
 * @date 2022-11-21
 */
@Configuration(proxyBeanMethods = false)
public class DictionaryAutoConfiguration {
    //集群模式钩子

    //挂在字典策略(unModify)

    //注册集群监听器

    //注册缓存监听器

    //加载innerDictionary

    //加载dbDictionary

    //冲突检测

    //冲突策略

    //缓存初始化

    //异常终止钩子
    @Bean
    @ConditionalOnMissingBean
    public DictionaryService dictService(SugarcoatDictionaryGroupRepository sugarcoatDictionaryGroupRepository,
                                         SugarcoatDictionaryRepository sugarcoatDictionaryRepository) {
        return new SugarcoatDictionaryServiceImpl(sugarcoatDictionaryGroupRepository, sugarcoatDictionaryRepository);
    }

}
