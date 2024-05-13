package com.sugarweb.framework.security.oauth2;

import java.util.List;

/**
 * @author lengleng
 * @date 2020/12/05
 */
public interface ClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 */
	SysOauthClientDetails getClientDetailsById(String clientId);

	/**
	 * 查询全部客户端
	 */
	List<SysOauthClientDetails> listClientDetails();

}
