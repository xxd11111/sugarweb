package com.xxd.server.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.protocol.common.PageData;
import com.sugarcoat.protocol.common.PageDto;
import com.sugarcoat.protocol.exception.ValidateException;
import com.xxd.orm.ExpressionWrapper;
import com.xxd.orm.PageDataConvert;
import com.xxd.server.domain.QSgcApi;
import com.xxd.server.service.dto.SgcApiDto;
import com.xxd.server.service.dto.SgcApiQueryDto;
import com.xxd.server.service.SgcApiService;
import com.xxd.server.domain.SgcApi;
import com.xxd.server.domain.SgcApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 * @since 2023/5/8
 */
@RequiredArgsConstructor
public class SgcApiServiceImpl implements SgcApiService {

	private final SgcApiRepository sgcApiRepository;

	@Override
	public SgcApiDto findOne(String id) {
		SgcApi sgcApi = sgcApiRepository.findById(id)
				.orElseThrow(() -> new ValidateException("serverApi not find"));
		SgcApiDto sgcApiDTO = new SgcApiDto();
		sgcApiDTO.setId(sgcApi.getOperationId());
		sgcApiDTO.setCode(sgcApi.getOperationId());
		sgcApiDTO.setName(sgcApi.getSummary());
		sgcApiDTO.setUrl(sgcApi.getUrl());
		sgcApiDTO.setMethodType(sgcApi.getRequestMethod());
		return sgcApiDTO;
	}

	@Override
	public PageData<SgcApiDto> findPage(PageDto pageDto, SgcApiQueryDto queryDto) {
		QSgcApi sgcApi = QSgcApi.sgcApi;
		PageRequest pageRequest = PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize())
				.withSort(Sort.Direction.DESC, sgcApi.url.getMetadata().getName());

		BooleanExpression expression = ExpressionWrapper.of()
				// .and(StrUtil.isNotEmpty(queryVO.getCode()),
				// serverApi.code.eq(queryVO.getCode()))
				// .and(StrUtil.isNotEmpty(queryVO.getName()),
				// serverApi.name.like(queryVO.getName()))
				// .and(StrUtil.isNotEmpty(queryVO.getUrl()),
				// serverApi.url.like(queryVO.getUrl()))
				// .and(StrUtil.isNotEmpty(queryVO.getStatus()),
				// serverApi.status.eq(queryVO.getStatus()))
				// .and(StrUtil.isNotEmpty(queryVO.getMethodType()),
				// serverApi.methodType.eq(queryVO.getMethodType()))
				.build();
		Page<SgcApiDto> page = sgcApiRepository.findAll(expression, pageRequest).map(entity -> {
			SgcApiDto sgcApiDTO = new SgcApiDto();
			sgcApiDTO.setId(entity.getOperationId());
			sgcApiDTO.setCode(entity.getSummary());
			sgcApiDTO.setName(entity.getOperationDescription());
			sgcApiDTO.setUrl(entity.getUrl());
			sgcApiDTO.setMethodType(entity.getRequestMethod());
			return sgcApiDTO;
		});
		return PageDataConvert.convert(page, SgcApiDto.class);
	}

}
