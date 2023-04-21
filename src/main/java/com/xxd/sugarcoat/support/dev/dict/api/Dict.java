package com.xxd.sugarcoat.support.dev.dict.api;

import com.xxd.sugarcoat.support.dev.orm.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author xxd
 * @version 1.0
 * @description: 基础字典信息
 * @date 2023/3/20
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Dict extends BaseEntity {

    @Column(length = 32)
    private String code;
    @Column(length = 32)
    private String name;

}
