package com.sugarweb.framework.security;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    public List<String> getPermissionList(Object loginId, String loginType) {
        return SecurityHelper.getLoginUser().getPermissions();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    public List<String> getRoleList(Object loginId, String loginType) {
        return SecurityHelper.getLoginUser().getRoles();
    }

}
