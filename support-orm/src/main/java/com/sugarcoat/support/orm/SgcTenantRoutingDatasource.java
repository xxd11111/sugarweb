package com.sugarcoat.support.orm;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;

/**
 * SgcTenantRoutingDatasource
 *
 * @author 许向东
 * @date 2023/11/1
 */
public class SgcTenantRoutingDatasource extends AbstractRoutingDataSource {

    private final CurrentTenantIdentifierResolver tenantIdResolver;

    public SgcTenantRoutingDatasource(CurrentTenantIdentifierResolver tenantIdResolver, DynamicDataSourceProperties dynamicDataSourceProperties) {
        this.tenantIdResolver = tenantIdResolver;
        SgcTenantDataSourceInfo master = dynamicDataSourceProperties.getDynamic().get("master");
        HikariDataSource hikariDataSource = DataSourceFactory.create(master);
        hikariDataSource.validate();
        setDefaultTargetDataSource(hikariDataSource);
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        setTargetDataSources(targetDataSources);
    }

    @Override
    protected String determineCurrentLookupKey() {
        //todo 判断是否是公共库
        return tenantIdResolver.resolveCurrentTenantIdentifier();
    }

}
