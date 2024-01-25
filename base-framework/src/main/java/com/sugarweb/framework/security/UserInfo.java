package com.sugarweb.framework.security;

/**
 * 用户信息，自定义用户需要实现该接口功能，作为全局用户的管理
 *
 * @author xxd
 * @version 1.0
 */
public interface UserInfo {

    String getId();

    String getUsername();

}
