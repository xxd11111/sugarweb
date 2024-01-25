package com.sugarweb.server.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.common.PageRequest;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.framework.orm.ExpressionWrapper;
import com.sugarweb.framework.orm.PageDataConvert;
import com.sugarweb.server.domain.QApiInfo;
import com.sugarweb.server.application.dto.ApiInfoDto;
import com.sugarweb.server.application.dto.ApiInfoQueryDto;
import com.sugarweb.server.application.ApiService;
import com.sugarweb.server.domain.ApiInfo;
import com.sugarweb.server.domain.ApiInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 */
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

	private final ApiInfoRepository sgcApiInfoRepository;

	@Override
	public ApiInfoDto findOne(String id) {
		ApiInfo apiInfo = sgcApiInfoRepository.findById(id)
				.orElseThrow(() -> new ValidateException("serverApi not find"));
		ApiInfoDto apiInfoDTO = new ApiInfoDto();
		apiInfoDTO.setId(apiInfo.getOperationId());
		apiInfoDTO.setCode(apiInfo.getOperationId());
		apiInfoDTO.setName(apiInfo.getSummary());
		apiInfoDTO.setUrl(apiInfo.getUrl());
		apiInfoDTO.setMethodType(apiInfo.getRequestMethod());
		return apiInfoDTO;
	}

	@Override
	public PageData<ApiInfoDto> findPage(PageRequest pageDto, ApiInfoQueryDto queryDto) {
		QApiInfo sgcApi = QApiInfo.apiInfo;
		org.springframework.data.domain.PageRequest pageRequest = org.springframework.data.domain.PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, sgcApi.url.getMetadata().getName());

		BooleanExpression expression = ExpressionWrapper.of()
				.build();
		Page<ApiInfoDto> page = sgcApiInfoRepository.findAll(expression, pageRequest).map(entity -> {
			ApiInfoDto apiInfoDTO = new ApiInfoDto();
			apiInfoDTO.setId(entity.getOperationId());
			apiInfoDTO.setCode(entity.getSummary());
			apiInfoDTO.setName(entity.getOperationDescription());
			apiInfoDTO.setUrl(entity.getUrl());
			apiInfoDTO.setMethodType(entity.getRequestMethod());
			return apiInfoDTO;
		});
		return PageDataConvert.convert(page, ApiInfoDto.class);
	}

	@Override
	public Optional<ApiInfo> findApiByUrl(String url) {
		BooleanExpression eq = QApiInfo.apiInfo.url.eq(url);
		return sgcApiInfoRepository.findOne(eq);
	}

}
