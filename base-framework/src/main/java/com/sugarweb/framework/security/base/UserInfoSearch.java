package com.sugarweb.framework.security.base;

import lombok.Data;

/**
 * 用户详情查询
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class UserInfoSearch {

    private String userId;

    private String username;

    private String phone;

}
