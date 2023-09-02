package com.sugarcoat.support.dict.domain;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.api.dict.DictionaryManager;
import com.sugarcoat.api.exception.FrameworkException;
import com.sugarcoat.support.dict.DictionaryProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 默认字典初始化
 *
 * @author xxd
 * @since 2023/9/2 21:36
 */
@Slf4j
public class DefaultDictionaryInit implements DictionaryInit{

    /**
     * 加载策略 关闭  只新增  智能合并（已修改过的不更改）  覆盖
     */
    private final String registerStrategy;

    private final DictionaryScanner dictionaryScanner;

    private final Map<String, DictionaryRegistry> dictionaryRegistryMap;

    private final DictionaryManager dictionaryManager;

    private final DictionaryCacheManager dictionaryCacheManager;

    public DefaultDictionaryInit(DictionaryProperties properties,
                                 DictionaryScanner dictionaryScanner,
                                 Map<String, DictionaryRegistry> dictionaryRegistryMap,
                                 DictionaryManager dictionaryManager,
                                 DictionaryCacheManager dictionaryCacheManager) {
        this.dictionaryCacheManager = dictionaryCacheManager;
        log.info("开始初始化 dictionary 配置");
        if (properties == null){
            throw new FrameworkException("DictionaryProperties can't be null, please check your setting!");
        }
        this.registerStrategy = properties.getRegisterStrategy();
        this.dictionaryScanner = dictionaryScanner;
        this.dictionaryRegistryMap = dictionaryRegistryMap;
        this.dictionaryManager = dictionaryManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!StrUtil.equals(registerStrategy, DictionaryRegistryStrategy.DISABLE.name())){
            innerDictionaryLoad();
        }
        Collection<DictionaryGroup> all = dictionaryManager.getAll();
        //todo 开启缓存需要加载外部字典数据，沿用redisson

        log.info("结束初始化 dictionary 配置");
    }

    /**
     * 内部字典数据加载
     */
    private void innerDictionaryLoad(){
        //获取扫描结果
        List<SugarcoatDictionaryGroup> dictionaryGroups = dictionaryScanner.scan();
        if (CollUtil.isEmpty(dictionaryGroups)){
            return;
        }
        //根据注册策略选择实现类
        DictionaryRegistry dictionaryRegistry = dictionaryRegistryMap.get(registerStrategy);
        if (dictionaryRegistry == null){
            throw new FrameworkException("not find dictionaryRegistry, check your setting!");
        }
        //注册字典
        dictionaryRegistry.register(dictionaryGroups);
        log.info("系统内置字典加载成功");
    }

}
