package com.sugarcoat.api.param;

import java.util.Optional;

/**
 * 参数管理
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/31
 */
public interface ParameterManager {

	String getValue(String code);

	String save(String code, String value);

	void remove(String code);

	Optional<Parameter> getParameter(String code);

}
