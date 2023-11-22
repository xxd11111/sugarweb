package com.sugarcoat.support.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarcoat.protocol.dict.DictionaryGroup;
import com.sugarcoat.protocol.dict.DictionaryManager;
import com.sugarcoat.protocol.exception.FrameworkException;
import com.sugarcoat.support.dict.domain.*;
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
public class DefaultDictionaryRunner implements DictionaryRunner {

    /**
     * 加载策略 关闭  只新增  智能合并（已修改过的不更改）  覆盖
     */
    private final String registerStrategy;

    /**
     * 是否启用缓存
     */
    private final boolean enableCache;

    private final DictionaryScanner dictionaryScanner;

    private final Map<String, DictionaryRegistry> dictionaryRegistryMap;

    private final DictionaryManager dictionaryManager;

    private final DictionaryCacheManager dictionaryCacheManager;

    public DefaultDictionaryRunner(DictionaryProperties properties,
                                   DictionaryScanner dictionaryScanner,
                                   Map<String, DictionaryRegistry> dictionaryRegistryMap,
                                   DictionaryManager dictionaryManager,
                                   DictionaryCacheManager dictionaryCacheManager) {
        this.dictionaryCacheManager = dictionaryCacheManager;
        if (properties == null){
            throw new FrameworkException("DictionaryProperties can't be null, please check your setting!");
        }
        this.registerStrategy = properties.getRegisterStrategy();
        this.enableCache = properties.isEnableCache();
        this.dictionaryScanner = dictionaryScanner;
        this.dictionaryRegistryMap = dictionaryRegistryMap;
        this.dictionaryManager = dictionaryManager;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化 dictionary 配置");
        //系统内置字典注册
        if (!StrUtil.equals(registerStrategy, DictionaryRegistryStrategy.DISABLE.name())){
            innerDictionaryRegistry();
        }
        //字典缓存配置
        if (enableCache){
            reload();
        }
        log.info("结束初始化 dictionary 配置");
    }

    /**
     * 内部字典数据加载
     */
    private void innerDictionaryRegistry(){
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
        dictionaryRegistry.save(dictionaryGroups);
        log.info("系统内置字典注册成功");
    }

    /**
     * 重载字典
     */
    private void reload(){
        //获取全部
        Collection<DictionaryGroup> all = dictionaryManager.getAll();
        //删除旧的缓存
        dictionaryCacheManager.clean();
        //保存缓存
        dictionaryCacheManager.put(all);
        log.info("字典缓存重载成功");
    }

}
