package com.sugarweb.framework.security;

import lombok.Data;

/**
 * 用户信息，自定义用户需要实现该接口功能，作为全局用户的管理
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserInfo {

    private String id;

    private String username;

}
