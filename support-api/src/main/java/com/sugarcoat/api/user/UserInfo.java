package com.sugarcoat.api.user;

/**
 * 用户信息 todo 移除该设计
 *
 * @author xxd
 * @date 2022-12-16
 */
public interface UserInfo {

	String getId();

	String getUsername();

	String getUserType();

	boolean isAdmin();

	boolean isSuperAdmin();

}
