package com.sugarcoat.support.orm;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.HashMap;
import java.util.Map;

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

        HikariDataSource defaultDs = DataSourceFactory.create(dynamicDataSourceProperties);
        defaultDs.validate();
        setDefaultTargetDataSource(defaultDs);
        Map<String, SgcDataSourceProperties> dynamic = dynamicDataSourceProperties.getDynamic();
        HashMap<Object, Object> targetDataSources = new HashMap<>();
        dynamic.forEach((k,v)->{
            HikariDataSource hikariDataSource = DataSourceFactory.create(v);
            hikariDataSource.validate();
            targetDataSources.put(k, hikariDataSource);
        });
        setTargetDataSources(targetDataSources);
    }

    @Override
    protected String determineCurrentLookupKey() {
        return DataSourceContext.getDsId();
    }

}
