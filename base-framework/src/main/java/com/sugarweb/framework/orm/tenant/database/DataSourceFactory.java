package com.sugarweb.framework.orm.tenant.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DataSourceFactory
 *
 * @author xxd
 * @version 1.0
 */
public class DataSourceFactory {

    public static HikariDataSource create(TenantDataSourceInfo tenantDataSourceInfo){
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(tenantDataSourceInfo.getDriverClassName());
        hikariConfig.setJdbcUrl(tenantDataSourceInfo.getJdbcUrl());
        hikariConfig.setUsername(tenantDataSourceInfo.getUsername());
        hikariConfig.setPassword(tenantDataSourceInfo.getPassword());

        hikariConfig.setPoolName(tenantDataSourceInfo.getPoolName());
        hikariConfig.setMaximumPoolSize(tenantDataSourceInfo.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(tenantDataSourceInfo.getMinimumIdle());

        hikariConfig.setConnectionTimeout(tenantDataSourceInfo.getConnectionTimeout());
        hikariConfig.setValidationTimeout(tenantDataSourceInfo.getValidationTimeout());
        hikariConfig.setIdleTimeout(tenantDataSourceInfo.getIdleTimeout());
        hikariConfig.setMaxLifetime(tenantDataSourceInfo.getMaxLifetime());
        hikariConfig.setKeepaliveTime(tenantDataSourceInfo.getKeepaliveTime());

        return new HikariDataSource(hikariConfig);
    }
}
