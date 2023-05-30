package com.sugarcoat.orm;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xxd
 * @description 基础实体类
 * @date 2022-11-12
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class EntityExt {
    @CreatedBy
    @Column(length = 32)
    private String createBy;

    @CreatedDate
    private Date createDate;

    @LastModifiedBy
    @Column(length = 32)
    private String updateBy;

    @LastModifiedDate
    private Date updateDate;

    @Column(length = 1)
    private String delFlag;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
