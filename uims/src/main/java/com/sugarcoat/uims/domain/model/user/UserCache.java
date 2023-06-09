package com.sugarcoat.uims.domain.model.user;

import lombok.Data;

import java.util.Collection;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-07
 */
@Data
public class UserCache {
    private String userId;
    private String username;
    private Collection<String> apis;
    private Collection<String> roles;
}
