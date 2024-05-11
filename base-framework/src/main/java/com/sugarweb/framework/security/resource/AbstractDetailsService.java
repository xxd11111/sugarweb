package com.sugarweb.framework.security.resource;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.common.Flag;
import com.sugarweb.framework.common.R;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

/**
 * @author lengleng
 * @date 2021/12/21
 */
public interface AbstractDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userdetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(R<UserInfo> result) {
		UserInfo info = Optional.ofNullable(result.getData()).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

		//从请求获取的
		Set<String> dbAuthsSet = new HashSet<>();
		List<String> roles = new ArrayList<>();
		List<String> permissions = new ArrayList<>();
		SysUser user = new SysUser();

		if (ArrayUtil.isNotEmpty(roles)) {
			// 获取角色
			roles.forEach(role -> dbAuthsSet.add("ROLE_" + role));
			// 获取资源
			dbAuthsSet.addAll(permissions);

		}

		Collection<GrantedAuthority> authorities = AuthorityUtils
			.createAuthorityList(dbAuthsSet.toArray(new String[0]));

		// 构造security用户
		return new UserInfo(user.getUserId(), user.getDeptId(), user.getUsername(),
				"{bcrypt}" + user.getPassword(), user.getPhone(), true, true, true,
				StrUtil.equals(user.getLockFlag(), Flag.FALSE.getValue()), authorities);
	}

	/**
	 * 通过用户实体查询
	 */
	default UserDetails loadUserByUser(UserInfo userInfo) {
		return this.loadUserByUsername(userInfo.getUsername());
	}

}
