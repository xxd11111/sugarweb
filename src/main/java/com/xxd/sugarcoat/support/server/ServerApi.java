package com.xxd.sugarcoat.support.server;

import com.xxd.sugarcoat.support.orm.BaseEntity;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-25
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ServerApi extends BaseEntity {

    private String name;

    private String code;

    private String url;

    private String methodType;

    private String remark;

    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ServerApi serverApi = (ServerApi) o;
        return getId() != null && Objects.equals(getId(), serverApi.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
