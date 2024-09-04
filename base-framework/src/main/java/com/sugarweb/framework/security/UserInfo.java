package com.sugarweb.framework.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息，自定义用户需要满足以下信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class UserInfo {

    private Map<String, Object> attributes = new HashMap<>();

    private String id;

    private String username;

    private List<String> roles;

    private List<String> permissions;

    public UserInfo(String id, String username, List<String> roles, List<String> permissions) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.permissions = permissions;
    }

}
