package com.sugarcoat.support.orm.tenant.database;

import lombok.Data;

/**
 * 数据源配置
 *
 * @author 许向东
 * @date 2023/11/4
 */
@Data
public class SgcDataSourceProperties {

    private String driverClassName;
    private String jdbcUrl;
    private String username;
    private String password;

    //  连接池的用户定义名称，主要出现在日志记录和JMX管理控制台中以识别池和池配置 HikariPool-1
    private String poolName;

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

}
