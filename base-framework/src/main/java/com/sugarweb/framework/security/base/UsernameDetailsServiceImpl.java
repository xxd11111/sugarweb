/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
