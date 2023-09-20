package com.sugarcoat.support.server.application.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.orm.PageDataConvert;
import com.sugarcoat.orm.ExpressionWrapper;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDto;
import com.sugarcoat.api.server.exception.ValidateException;
import com.sugarcoat.support.server.application.dto.ServerApiDto;
import com.sugarcoat.support.server.application.dto.ServerApiQueryDto;
import com.sugarcoat.support.server.application.ServerApiService;
import com.sugarcoat.support.server.domain.QServerApi;
import com.sugarcoat.support.server.domain.ServerApi;
import com.sugarcoat.support.server.domain.ServerApiRepository;
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
public class ServerApiServiceImpl implements ServerApiService {

	private final ServerApiRepository serverApiRepository;

	@Override
	public ServerApiDto findOne(String id) {
		ServerApi serverApi = serverApiRepository.findById(id)
				.orElseThrow(() -> new ValidateException("serverApi not find"));
		ServerApiDto serverApiDTO = new ServerApiDto();
		serverApiDTO.setId(serverApi.getId());
		serverApiDTO.setCode(serverApi.getCode());
		serverApiDTO.setName(serverApi.getName());
		serverApiDTO.setUrl(serverApi.getUrl());
		serverApiDTO.setMethodType(serverApi.getMethodType());
		serverApiDTO.setRemark(serverApi.getRemark());
		return serverApiDTO;
	}

	@Override
	public PageData<ServerApiDto> findPage(PageDto pageDto, ServerApiQueryDto queryDto) {
		QServerApi serverApi = QServerApi.serverApi;
		PageRequest pageRequest = PageRequest.of(pageDto.getPage(), pageDto.getSize())
				.withSort(Sort.Direction.DESC, serverApi.url.getMetadata().getName());

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
		Page<ServerApiDto> page = serverApiRepository.findAll(expression, pageRequest).map(entity -> {
			ServerApiDto serverApiDTO = new ServerApiDto();
			serverApiDTO.setId(entity.getId());
			serverApiDTO.setCode(entity.getCode());
			serverApiDTO.setName(entity.getName());
			serverApiDTO.setUrl(entity.getUrl());
			serverApiDTO.setRemark(entity.getRemark());
			serverApiDTO.setMethodType(entity.getMethodType());
			return serverApiDTO;
		});
		return PageDataConvert.convert(page, ServerApiDto.class);
	}

}
