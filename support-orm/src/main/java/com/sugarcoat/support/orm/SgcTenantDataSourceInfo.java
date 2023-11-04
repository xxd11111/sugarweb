package com.sugarcoat.support.orm;

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
public class SgcTenantDataSourceInfo extends SgcDataSourceProperties{

    @Id
    private String id;

    private String tenantId;

    // //  是否注册JMX管理Bean（MBeans） FALSE
    // private String registerMbeans;
    // //  控制池是否可以通过JMX暂停和恢复 FALSE
    // private String allowPoolSuspension;
    // //  记录消息之前连接可能离开池的时间量，表示可能的连接泄漏 	0 如果大于0且不是单元测试，则进一步判断：(leakDetectionThreshold < SECONDS.toMillis(2) or (leakDetectionThreshold > maxLifetime && maxLifetime > 0)，会被重置为0 . 即如果要生效则必须>0，而且不能小于2秒，而且当maxLifetime > 0时不能大于maxLifetime
    // private String leakDetectionThreshold;

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
