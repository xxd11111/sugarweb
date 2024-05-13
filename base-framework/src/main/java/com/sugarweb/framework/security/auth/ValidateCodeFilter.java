package com.sugarweb.framework.security.auth;

/**
 * 登录前处理器
 *
 * @author lengleng
 * @date 2024/4/3
 */

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.utils.BeanUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author lbw
 * @date 2024-01-06
 * <p>
 * 登录前置处理器： 前端密码传输密文解密，验证码处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter {

	private final AuthSecurityConfigProperties authSecurityConfigProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestUrl = request.getServletPath();

		// 不是登录URL 请求直接跳过
		if (!"/oauth2/token".equals(requestUrl)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 如果登录URL 但是刷新token的请求，直接向下执行
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (StrUtil.equals("refresh_token", grantType)) {
			filterChain.doFilter(request, response);
			return;
		}

		// 客户端配置跳过验证码
		boolean isIgnoreClient = authSecurityConfigProperties.getIgnoreClients().contains(getClientId());
		if (isIgnoreClient) {
			filterChain.doFilter(request, response);
			return;
		}

		// 校验验证码 1. 客户端开启验证码 2. 短信模式
		try {
			checkCode();
			filterChain.doFilter(request, response);
		}
		catch (ValidateException validateCodeException) {
			throw new OAuth2AuthenticationException(validateCodeException.getMessage());
		}
	}

	/**
	 * 校验验证码
	 */
	private void checkCode() throws ValidateException {
		Optional<HttpServletRequest> request = getRequest();
		String code = request.get().getParameter("code");

		if (StrUtil.isBlank(code)) {
			throw new ValidateException("验证码不能为空");
		}

		String randomStr = request.get().getParameter("randomStr");

		// https://gitee.com/log4j/pig/issues/IWA0D
		String mobile = request.get().getParameter("mobile");
		if (StrUtil.isNotBlank(mobile)) {
			randomStr = mobile;
		}

		String key = "DEFAULT_CODE_KEY:" + randomStr;
		RedisTemplate<String, String> redisTemplate = BeanUtil.getBean(RedisTemplate.class);
		if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
			throw new ValidateException("验证码不合法");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj == null) {
			throw new ValidateException("验证码不合法");
		}

		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new ValidateException("验证码不合法");
		}

		if (!StrUtil.equals(saveCode, code)) {
			redisTemplate.delete(key);
			throw new ValidateException("验证码不合法");
		}

		redisTemplate.delete(key);
	}

	public String getClientId() {
		if (getRequest().isPresent()) {
			String header = getRequest().get().getHeader(HttpHeaders.AUTHORIZATION);
			return splitClient(header)[0];
		}
		return null;
	}

	public Optional<HttpServletRequest> getRequest() {
		return Optional
				.ofNullable(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
	}

	private static String[] splitClient(String header) {
		if (header == null || !header.startsWith("Basic ")) {
			throw new ValidateException("请求头中client信息为空");
		}
		byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new ValidateException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, StandardCharsets.UTF_8);

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new ValidateException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}
}
