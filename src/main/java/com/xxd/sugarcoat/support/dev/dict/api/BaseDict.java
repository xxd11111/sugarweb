package com.xxd.sugarcoat.support.dev.dict.api;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

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
public class BaseDict {
    @Id
    private String id;
    private String type;
    private String code;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseDict baseDict = (BaseDict) o;
        return id != null && Objects.equals(id, baseDict.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
