package com.sugarcoat.support.orm;

import lombok.NonNull;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/1
 */
public class SgcTenantRoutingDatasource extends AbstractRoutingDataSource {

    private final SgcTenantIdResolver tenantIdResolver;


    public SgcTenantRoutingDatasource(SgcTenantIdResolver tenantIdResolver) {
        this.tenantIdResolver = tenantIdResolver;
        // setDefaultTargetDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        setTargetDataSources(targetDataSources);
    }


    @Override
    protected String determineCurrentLookupKey() {
        return tenantIdResolver.resolveCurrentTenantIdentifier();
    }

}
