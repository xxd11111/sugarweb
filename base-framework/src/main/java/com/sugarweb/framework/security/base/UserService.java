package com.sugarweb.framework.security.base;

/**
 * @author lengleng
 * @date 2018/6/22
 */
public interface UserService {

    /**
     * 通过用户名查询用户、角色信息
     */
    UserInfo info(UserInfoSearch search);


    /**
     * 锁定用户
     *
     * @param username 用户名
     * @param from     调用标识
     * @return
     */
    Boolean lockUser(String username, String from);

}
