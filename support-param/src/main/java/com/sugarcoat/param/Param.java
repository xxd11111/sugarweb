package com.sugarcoat.param;

import com.sugarcoat.orm.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author xxd
 * @description 参数dto
 * @date 2022-11-18
 */
@Entity
@Getter
@Setter
@ToString
public class Param extends BaseEntity {
    private String code;
    private String name;
    private String value;
    private String comment;
    private String defaultValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Param param = (Param) o;
        return getId() != null && Objects.equals(getId(), param.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void resetValue(){
        value = defaultValue;
    }
}