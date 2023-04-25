package com.xxd.sugarcoat.support.dev.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.xxd.sugarcoat.support.dev.param.api.Param;
import com.xxd.sugarcoat.support.dev.param.api.ParamDTO;
import com.xxd.sugarcoat.support.dev.param.api.ParamService;
import com.xxd.sugarcoat.support.dev.param.api.ParamVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxd
 * @description 默认缓存实现方法
 * @date 2022-11-19
 */
@Component
public class DefaultParamServiceImpl implements ParamService {

    private final Map<String, String> paramMap = new ConcurrentHashMap<>();

    @Override
    public void saveParam(ParamDTO param) {
        if (param != null && StrUtil.isNotBlank(param.getCode())) {
            paramMap.put(param.getCode(), param.getValue());
        }
    }

    @Override
    public void removeParam(String key) {
        paramMap.remove(key);
    }

    @Override
    public ParamVO getParam(String key) {
        return null;
    }

    @Override
    public Map<String, String> getParamMap() {
        return null;
    }

    @Override
    public List<Param> getAll() {
        return null;
    }

    //@Override
    //public Map<String, String> getParam() {
    //    Map<String, String> target = new HashMap<>();
    //    //String时可以使用此方法，若为引用对象时注意深拷贝问题
    //    BeanUtil.copyProperties(paramMap, target);
    //    return target;
    //}

    //@Override
    //public void clear() {
    //    paramMap.clear();
    //}
}
