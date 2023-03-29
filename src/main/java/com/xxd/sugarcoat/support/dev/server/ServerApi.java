package com.xxd.sugarcoat.support.dev.server;

import com.xxd.sugarcoat.support.undo.status.AccessibleEnum;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
public class ServerApi {

    @Id
    private String id;

    private String name;

    private String code;

    private String url;

    private String methodType;

    private String remark;

    @Enumerated(EnumType.STRING)
    private AccessibleEnum status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ServerApi serverApi = (ServerApi) o;
        return id != null && Objects.equals(id, serverApi.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
