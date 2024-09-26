package com.sugarweb.framework.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 用户信息，自定义用户需要满足以下信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class LoginUser {

    private String userId;

    private String username;

    private List<String> roles;

    private List<String> permissions;

    private Map<String, Object> attributes = new HashMap<>();

    public LoginUser(String userId, String username, List<String> roles, List<String> permissions) {
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
    }

}
