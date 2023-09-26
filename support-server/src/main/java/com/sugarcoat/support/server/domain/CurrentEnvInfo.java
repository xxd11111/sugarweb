package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.EnvInfo;

/**
 * 当前环境信息 todo 延期
 *
 * @author 许向东
 * @date 2023/9/22
 */
public class CurrentEnvInfo implements EnvInfo {

    @Override
    public Object getRuntimeInfo() {
        return null;
    }

    @Override
    public Object getMemoryInfo() {
        return null;
    }

    @Override
    public Object getCpuInfo() {
        return null;
    }

    @Override
    public Object getNetworkInfo() {
        return null;
    }

    @Override
    public Object getDiskInfo() {
        return null;
    }

}
