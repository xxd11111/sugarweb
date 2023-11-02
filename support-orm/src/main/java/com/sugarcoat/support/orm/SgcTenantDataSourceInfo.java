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
public class SgcTenantDataSourceInfo {

    @Id
    private String dsId;
    private String tenantId;
    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;

    //  连接池的用户定义名称，主要出现在日志记录和JMX管理控制台中以识别池和池配置 HikariPool-1
    private String poolName;
    //  该属性为支持模式概念的数据库设置默认模式 driver default
    private String schema;
    //  该属性设置一个SQL语句，在将每个新连接创建后，将其添加到池中之前执行该语句.如果此查询失败，它将被视为连接尝试失败。 null
    private String connectionInitSql;
    //  设置要执行的 SQL 查询以测试连接的有效性。
    private String connectionTestQuery;

    //  池中最大连接数，包括闲置和使用中的连接 -1 如果maxPoolSize小于1，则会被重置。当minIdle<=0被重置为DEFAULT_POOL_SIZE则为10;如果minIdle>0则重置为minIdle的值
    private Integer maximumPoolSize;
    //  池中维护的最小空闲连接数 -1 	minimumIdle<0 或者 minimumIdle>maxPoolSize,则被重置为maxPoolSize
    private Integer minimumIdle;

    //  等待来自池的连接的最大毫秒数 30000 如果小于250毫秒，则被重置回30秒
    private Long connectionTimeout;
    //  连接允许在池中闲置的最长时间 600000 	如果idleTimeout+1秒>maxLifetime 且 maxLifetime>0，则会被重置为0（代表永远不会退出）；如果idleTimeout!=0且小于10秒，则会被重置为10秒
    private Long idleTimeout;
    //  池中连接最长生命周期 1800000	如果不等于0且小于30秒则会被重置回30分钟
    private Long maxLifetime;
    // 此属性控制池中连接的 keepalive 间隔。keepalive 线程永远不会测试正在使用的连接，只有当它处于空闲状态时才会对其进行测试。值以毫秒为单位，默认值为 0（禁用）
    private Long keepaliveTime;
    // 连接将被测试活动的最大时间量 5000 如果小于250毫秒，则会被重置回5秒
    private Long validationTimeout;

    //  是否注册JMX管理Bean（MBeans） FALSE
    private String registerMbeans;
    //  控制池是否可以通过JMX暂停和恢复 FALSE
    private String allowPoolSuspension;
    //  记录消息之前连接可能离开池的时间量，表示可能的连接泄漏 	0 如果大于0且不是单元测试，则进一步判断：(leakDetectionThreshold < SECONDS.toMillis(2) or (leakDetectionThreshold > maxLifetime && maxLifetime > 0)，会被重置为0 . 即如果要生效则必须>0，而且不能小于2秒，而且当maxLifetime > 0时不能大于maxLifetime
    private String leakDetectionThreshold;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SgcTenantDataSourceInfo that = (SgcTenantDataSourceInfo) o;
        return dsId != null && Objects.equals(dsId, that.dsId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
