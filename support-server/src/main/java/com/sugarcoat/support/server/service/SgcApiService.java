package com.sugarcoat.support.server.service;

import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.support.server.service.dto.SgcApiDto;
import com.sugarcoat.support.server.service.dto.SgcApiQueryDto;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 * @since 2023/3/13
 */
public interface SgcApiService {

	SgcApiDto findOne(String id);

	PageData<SgcApiDto> findPage(PageDto pageDto, SgcApiQueryDto queryVO);

}