package com.xxd.sugarcoat.support.dev.dict.model;

import com.xxd.sugarcoat.support.dev.orm.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Objects;

/**
 * @author xxd
 * @version 1.0
 * @description: 基础字典信息
 * @date 2023/3/20
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DictItem extends BaseEntity {

    @Column(length = 32)
    private String code;
    @Column(length = 32)
    private String name;
    @ManyToOne
    private DictGroup dictGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DictItem dictItem = (DictItem) o;
        return getId() != null && Objects.equals(getId(), dictItem.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
