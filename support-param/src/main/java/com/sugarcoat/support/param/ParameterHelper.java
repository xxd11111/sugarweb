package com.sugarcoat.support.param;

import com.sugarcoat.api.BeanUtil;
import com.sugarcoat.api.param.ParameterManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xxd
 * @description 参数使用帮助类
 * @since 2022-11-19
 */

@Slf4j
public class ParameterHelper {

	public static String getValue(String code) {
		return getInstance().getValue(code);
	}

	private static ParameterManager getInstance() {
		return ParameterHelperInner.PARAMETER_CLIENT;
	}

	private static class ParameterHelperInner {

		private static final ParameterManager PARAMETER_CLIENT = BeanUtil.getBean(ParameterManager.class);

	}

}
