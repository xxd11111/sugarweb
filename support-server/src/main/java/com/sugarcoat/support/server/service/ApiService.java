package com.sugarcoat.support.server.service;

import com.sugarcoat.protocol.common.PageDto;

/**
 * 应用接口信息服务
 *
 * @author xxd
 * @since 2023/9/20 23:11
 */
public interface ApiService {

    /**
     * 获取接口信息详情
     * @return 接口信息详情
     */
    Object findOne(String apiId);

    /**
     * 获取接口信息列表
     * @return 接口信息列表
     */
    Object findAll();

    /**
     * 获取接口信息分页
     * @return 接口信息分页
     */
    Object findPage(PageDto pageDto);

}
