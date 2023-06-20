package com.sugarcoat.api.user;

/**
 * 用户信息
 *
 * @author xxd
 * @date 2022-12-16
 */
public interface UserInfo {

	String getId();

	String getUsername();

	void checkCertificate(String certificate);

	String getUserType();

	boolean isAdmin();

	boolean isSuperAdmin();

}
