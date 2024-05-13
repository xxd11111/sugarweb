package com.sugarweb.framework.security.base;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class UsernameDetailsServiceImpl implements UserDetailsService {

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
		UserInfoSearch search = new UserInfoSearch();
		search.setUsername(username);
		UserInfo userDetails = userService.info(search);
		if (cache != null) {
			cache.put(username, userDetails);
		}
		return userDetails;
	}

}
