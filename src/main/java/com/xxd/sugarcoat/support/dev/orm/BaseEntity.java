package com.xxd.sugarcoat.support.dev.orm;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author xxd
 * @description 基础实体类
 * @date 2022-11-12
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
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

    private String delFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity that = (BaseEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
