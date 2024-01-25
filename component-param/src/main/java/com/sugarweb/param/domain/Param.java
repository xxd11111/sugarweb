package com.sugarweb.param.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * 参数实体
 *
 * @author xxd
 * @version 1.0
 */
@Entity
@Getter
@Setter
@ToString
public class Param {
    /**
     * id
     */
    @Id
    private String id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Param sugarcoatParam = (Param) o;
        return getId() != null && Objects.equals(getId(), sugarcoatParam.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}