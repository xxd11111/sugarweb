package com.sugarcoat.support.param;

import com.sugarcoat.api.BeanUtil;
import com.sugarcoat.api.param.ParamManager;
import lombok.extern.slf4j.Slf4j;

/**
 * 参数使用帮助类 todo
 *
 * @author xxd
 * @since 2022-11-19
 */

@Slf4j
public class ParameterHelper {

	public static String getValue(String code) {
		return getInstance().getValue(code);
	}

	private static ParamManager getInstance() {
		return ParameterHelperInner.PARAMETER_CLIENT;
	}

	private static class ParameterHelperInner {

		private static final ParamManager PARAMETER_CLIENT = BeanUtil.getBean(ParamManager.class);

	}

}
