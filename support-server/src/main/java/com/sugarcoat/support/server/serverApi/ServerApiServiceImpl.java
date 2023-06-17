package com.sugarcoat.support.server.serverApi;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.api.common.PageDataAdaptManager;
import com.sugarcoat.orm.ExpressionWrapper;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageParameter;
import com.sugarcoat.api.exception.ValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 接口服务
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/8
 */

@RequiredArgsConstructor
public class ServerApiServiceImpl implements ServerApiService {

	private final ServerApiRepository serverApiRepository;

	@Override
	public ServerApiDTO findOne(String id) {
		ServerApi serverApi = serverApiRepository.findById(id)
				.orElseThrow(() -> new ValidateException("serverApi not find"));
		ServerApiDTO serverApiDTO = new ServerApiDTO();
		serverApiDTO.setId(serverApi.getId());
		serverApiDTO.setCode(serverApi.getCode());
		serverApiDTO.setName(serverApi.getName());
		serverApiDTO.setUrl(serverApi.getUrl());
		serverApiDTO.setMethodType(serverApi.getMethodType());
		serverApiDTO.setRemark(serverApi.getRemark());
		serverApiDTO.setStatus(serverApi.getStatus());
		return serverApiDTO;
	}

	@Override
	public PageData<ServerApiDTO> findPage(PageParameter pageParameter, ServerApiQueryVO queryVO) {
		QServerApi serverApi = QServerApi.serverApi;
		PageRequest pageRequest = PageRequest.of(pageParameter.getPageNum(), pageParameter.getPageSize())
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
		Page<ServerApiDTO> page = serverApiRepository.findAll(expression, pageRequest).map(entity -> {
			ServerApiDTO serverApiDTO = new ServerApiDTO();
			serverApiDTO.setId(entity.getId());
			serverApiDTO.setCode(entity.getCode());
			serverApiDTO.setName(entity.getName());
			serverApiDTO.setUrl(entity.getUrl());
			serverApiDTO.setRemark(entity.getRemark());
			serverApiDTO.setStatus(entity.getStatus());
			serverApiDTO.setMethodType(entity.getMethodType());
			return serverApiDTO;
		});
		return PageDataAdaptManager.convert(page, ServerApiDTO.class);
	}

}
