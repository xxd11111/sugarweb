package com.xxd.server.application;

import com.xxd.common.PageData;
import com.xxd.common.PageRequest;
import com.xxd.server.application.dto.SgcApiDto;
import com.xxd.server.application.dto.SgcApiQueryDto;

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