package com.sugarcoat.support.orm.tenant.database;

import com.sugarcoat.support.orm.datasource.SgcDataSourceProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * 多租户数据源信息
 *
 * @author xxd
 * @since 2023/11/1 22:44
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class SgcTenantDataSourceInfo extends SgcDataSourceProperties {

    @Id
    private String id;

    private String tenantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SgcTenantDataSourceInfo that = (SgcTenantDataSourceInfo) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
