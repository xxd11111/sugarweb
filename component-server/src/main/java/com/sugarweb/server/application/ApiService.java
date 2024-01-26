package com.sugarweb.server.application;

import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.server.application.dto.ApiInfoDto;
import com.sugarweb.server.application.dto.ApiInfoQueryDto;
import com.sugarweb.server.domain.ApiInfo;

import java.util.Optional;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 */
public interface ApiService {

	ApiInfoDto findOne(String id);

	PageData<ApiInfoDto> findPage(PageQuery pageQuery, ApiInfoQueryDto queryVO);

	Optional<ApiInfo> findApiByUrl(String url);

}