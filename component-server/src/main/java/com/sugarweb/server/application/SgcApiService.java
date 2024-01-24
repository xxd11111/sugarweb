package com.sugarweb.server.application;

import com.sugarweb.common.PageData;
import com.sugarweb.common.PageRequest;
import com.sugarweb.server.application.dto.SgcApiDto;
import com.sugarweb.server.application.dto.SgcApiQueryDto;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 */
public interface SgcApiService {

	SgcApiDto findOne(String id);

	PageData<SgcApiDto> findPage(PageRequest pageRequest, SgcApiQueryDto queryVO);

}