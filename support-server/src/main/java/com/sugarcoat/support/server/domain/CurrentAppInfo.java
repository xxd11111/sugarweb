package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.AppInfo;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/9/22
 */
public class CurrentAppInfo implements AppInfo {

    @Override
    public Object getRuntimeInfo() {
        return null;
    }

    @Override
    public Object getJvmMemoryInfo() {
        return null;
    }

    @Override
    public Object getJvmConfigInfo() {
        return null;
    }

}
