package com.xxd.orm.tenant.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DataSourceFactory
 *
 * @author xxd
 * @version 1.0
 */
public class DataSourceFactory {

    public static HikariDataSource create(SgcDataSourceProperties sgcDsInfo){
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(sgcDsInfo.getDriverClassName());
        hikariConfig.setJdbcUrl(sgcDsInfo.getJdbcUrl());
        hikariConfig.setUsername(sgcDsInfo.getUsername());
        hikariConfig.setPassword(sgcDsInfo.getPassword());

        hikariConfig.setPoolName(sgcDsInfo.getPoolName());
        hikariConfig.setMaximumPoolSize(sgcDsInfo.getMaximumPoolSize());
        hikariConfig.setMinimumIdle(sgcDsInfo.getMinimumIdle());

        hikariConfig.setConnectionTimeout(sgcDsInfo.getConnectionTimeout());
        hikariConfig.setValidationTimeout(sgcDsInfo.getValidationTimeout());
        hikariConfig.setIdleTimeout(sgcDsInfo.getIdleTimeout());
        hikariConfig.setMaxLifetime(sgcDsInfo.getMaxLifetime());
        hikariConfig.setKeepaliveTime(sgcDsInfo.getKeepaliveTime());

        return new HikariDataSource(hikariConfig);
    }
}
