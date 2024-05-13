package com.sugarweb.framework.security.auth;

import com.sugarweb.framework.security.base.UserInfo;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * token 输出增强
 *
 * @author lengleng
 * @date 2022/6/3
 */
public class CustomeOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	/**
	 * Customize the OAuth 2.0 Token attributes.
	 * @param context the context containing the OAuth 2.0 Token attributes
	 */
	@Override
	public void customize(OAuth2TokenClaimsContext context) {
		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		claims.claim("license", "https://baidu.com");
		String clientId = context.getAuthorizationGrant().getName();
		claims.claim("clientId", clientId);
		// 客户端模式不返回具体用户信息
		if ("client_credentials".equals(context.getAuthorizationGrantType().getValue())) {
			return;
		}

		UserInfo user = (UserInfo) context.getPrincipal().getPrincipal();
		claims.claim("user_info", user);
		claims.claim("user_id", user.getId());
		claims.claim("username", user.getUsername());
	}

}
