package com.sugarcoat.support.orm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DataSourceFactory
 *
 * @author xxd
 * @since 2023/11/2 22:43
 */
public class DataSourceFactory {

    public static HikariDataSource create(SgcTenantDataSourceInfo sgcDsInfo){
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(sgcDsInfo.getDriverClassName());
        hikariConfig.setJdbcUrl(sgcDsInfo.getJdbcUrl());
        hikariConfig.setUsername(sgcDsInfo.getUsername());
        hikariConfig.setPassword(sgcDsInfo.getPassword());

        hikariConfig.setPoolName(sgcDsInfo.getPoolName());
        hikariConfig.setConnectionTestQuery(sgcDsInfo.getConnectionTestQuery());

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
