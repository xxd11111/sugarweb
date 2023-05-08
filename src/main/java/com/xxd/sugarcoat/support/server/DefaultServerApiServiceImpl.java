package com.xxd.sugarcoat.support.server;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.xxd.sugarcoat.support.server.api.ServerApiDTO;
import com.xxd.sugarcoat.support.server.api.ServerApiQueryVO;
import com.xxd.sugarcoat.support.server.api.ServerApiService;
import com.xxd.sugarcoat.support.orm.ExpressionWrapper;
import com.xxd.sugarcoat.support.servelt.exception.api.ValidateException;
import com.xxd.sugarcoat.support.servelt.protocol.PageData;
import com.xxd.sugarcoat.support.servelt.protocol.PageParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/8
 */
public class DefaultServerApiServiceImpl implements ServerApiService {
    ServerApiRepository serverApiRepository;

    @Override
    public void save(ServerApiDTO serverApi) {
        ServerApi entity = new ServerApi();
        entity.setId(serverApi.getId());
        entity.setCode(serverApi.getCode());
        entity.setName(serverApi.getName());
        entity.setUrl(serverApi.getUrl());
        entity.setMethodType(serverApi.getUrl());
        entity.setRemark(serverApi.getRemark());
        entity.setStatus(serverApi.getStatus());
        serverApiRepository.save(entity);
    }

    @Override
    public void remove(String id) {
        serverApiRepository.deleteById(id);
    }

    @Override
    public ServerApiDTO findOne(String id) {
        ServerApi serverApi = serverApiRepository.findById(id).orElseThrow(() -> {
            throw new ValidateException("serverApi not find");
        });
        ServerApiDTO serverApiDTO = new ServerApiDTO();
        serverApiDTO.setId(serverApi.getId());
        serverApiDTO.setCode(serverApi.getCode());
        serverApiDTO.setName(serverApi.getName());
        serverApiDTO.setUrl(serverApi.getUrl());
        serverApiDTO.setMethodType(serverApi.getUrl());
        serverApiDTO.setRemark(serverApi.getRemark());
        serverApiDTO.setStatus(serverApi.getStatus());
        return serverApiDTO;
    }

    @Override
    public PageData<ServerApiDTO> findPage(PageParam pageParam, ServerApiQueryVO queryVO) {
        QServerApi serverApi = QServerApi.serverApi;
        PageRequest pageRequest = PageRequest
                .of(pageParam.getPageNum(), pageParam.getPageSize())
                .withSort(Sort.Direction.DESC, serverApi.url.getMetadata().getName());

        BooleanExpression expression = ExpressionWrapper.of()
                .and(StrUtil.isNotEmpty(queryVO.getCode()), serverApi.code.eq(queryVO.getCode()))
                .and(StrUtil.isNotEmpty(queryVO.getName()), serverApi.name.like(queryVO.getName()))
                .and(StrUtil.isNotEmpty(queryVO.getUrl()), serverApi.url.like(queryVO.getUrl()))
                .and(StrUtil.isNotEmpty(queryVO.getStatus()), serverApi.status.eq(queryVO.getStatus()))
                .and(StrUtil.isNotEmpty(queryVO.getMethodType()), serverApi.methodType.eq(queryVO.getMethodType()))
                .build();
        serverApiRepository.findAll(expression, pageRequest);
        return null;
    }

}
