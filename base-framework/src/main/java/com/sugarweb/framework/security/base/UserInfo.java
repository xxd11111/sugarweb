package com.sugarweb.framework.security.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息，自定义用户需要满足以下信息
 *
 * @author xxd
 * @version 1.0
 */
public class UserInfo {

    /**
     * 扩展属性，方便存放oauth 上下文相关信息
     */
    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 用户ID
     */
    @Getter
    @JsonSerialize(using = ToStringSerializer.class)
    private final String id;

    @Getter
    @JsonSerialize(using = ToStringSerializer.class)
    private final String username;

    /**
     * 部门ID
     */
    @Getter
    @JsonSerialize(using = ToStringSerializer.class)
    private final String deptId;

    /**
     * 手机号
     */
    @Getter
    private final String phone;

    public UserInfo(String id, String deptId, String username, String password, String phone, boolean enabled,
                    boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, String username1) {
        this.id = id;
        this.deptId = deptId;
        this.phone = phone;
        this.username = username1;
    }

}
