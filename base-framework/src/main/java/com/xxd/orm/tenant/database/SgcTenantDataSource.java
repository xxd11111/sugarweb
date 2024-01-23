package com.xxd.orm.tenant.database;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * 多租户数据源信息
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@Entity
public class SgcTenantDataSource extends SgcDataSourceProperties {

    @Id
    private String id;

    private String tenantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SgcTenantDataSource that = (SgcTenantDataSource) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
