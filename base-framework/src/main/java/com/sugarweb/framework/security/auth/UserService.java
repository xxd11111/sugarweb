/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.sugarweb.framework.security.auth;

import com.sugarweb.framework.security.resource.SysUser;
import com.sugarweb.framework.security.resource.UserInfo;

/**
 * @author lengleng
 * @date 2018/6/22
 */
public interface UserService {

    /**
     * 通过用户名查询用户、角色信息
     *
     * @param user 用户查询对象
     * @param from 调用标志
     * @return R
     */
    UserInfo info(SysUser user, String from);

    /**
     * 锁定用户
     *
     * @param username 用户名
     * @param from     调用标识
     * @return
     */
    Boolean lockUser(String username, String from);

}
