package com.xxd.sugarcoat.support.dev.orm;

import lombok.Data;

/**
 * @author xxd
 * @description 基础实体类
 * @date 2022-11-12
 */
@Data
public class BaseEntity implements InsertAction, UpdateAction {

    private String createBy;

    private String createDate;

    private String updateBy;

    private String updateDate;

    @Override
    public void updateInit() {

    }

    @Override
    public void insertInit() {

    }
}
