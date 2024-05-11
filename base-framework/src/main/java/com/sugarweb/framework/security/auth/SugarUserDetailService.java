package com.sugarweb.framework.security.auth;

import com.sugarweb.framework.security.resource.SysUser;
import com.sugarweb.framework.security.resource.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * TODO
 *
 * @author 许向东
 * @version 1.0
 */
@RequiredArgsConstructor
public class SugarUserDetailService implements UserDetailsService {
    private final UserService userService;

    private final CacheManager cacheManager;

    /**
     * 用户名密码登录
     * @param username 用户名
     * @return
     */
    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Cache cache = cacheManager.getCache("USER_DETAILS");
        if (cache != null && cache.get(username) != null) {
            return (UserInfo) cache.get(username).get();
        }

        SysUser userDTO = new SysUser();
        userDTO.setUsername(username);
        UserInfo userDetails = userService.info(userDTO, "1");
        if (cache != null) {
            cache.put(username, userDetails);
        }
        return userDetails;
    }

}
