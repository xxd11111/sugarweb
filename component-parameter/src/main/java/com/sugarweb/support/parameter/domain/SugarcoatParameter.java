package com.sugarweb.support.parameter.domain;

import com.sugarweb.support.parameter.api.Parameter;
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
public class SugarcoatParameter implements Parameter {
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
        SugarcoatParameter sugarcoatParam = (SugarcoatParameter) o;
        return getId() != null && Objects.equals(getId(), sugarcoatParam.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}