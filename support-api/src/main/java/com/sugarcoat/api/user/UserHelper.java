package com.sugarcoat.api.user;

/**
 * 用户helper
 *
 * @author xxd
 * @version 1.0
 * @date 2023/3/9
 */
public class UserHelper {

	protected static UserClient userClient;

	private UserHelper() {
	}

	public static UserInfo currentAccount() {
		return userClient.currentUser();
	}

	public static String getUsername() {
		return userClient.currentUser().getUsername();
	}

	public static String getUserType() {
		return userClient.currentUser().getUserType();
	}

	public static String getId() {
		return userClient.currentUser().getId();
	}

	public static boolean isAdmin() {
		return userClient.currentUser().isAdmin();
	}

	public static boolean isSuperAdmin() {
		return userClient.currentUser().isSuperAdmin();
	}

}
