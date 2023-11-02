package com.sugarcoat.support.orm;

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

    private final SgcTenantIdResolver tenantIdResolver;


    public SgcTenantRoutingDatasource(SgcTenantIdResolver tenantIdResolver, SgcTenantDataSourceRepository tenantDataSourceRepository, PropertiesLoadDataSource propertiesLoadDataSource) {
        this.tenantIdResolver = tenantIdResolver;
        Iterable<SgcTenantDataSourceInfo> all = tenantDataSourceRepository.findAll();
        // setDefaultTargetDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        setTargetDataSources(targetDataSources);
    }


    @Override
    protected String determineCurrentLookupKey() {
        return tenantIdResolver.resolveCurrentTenantIdentifier();
    }

}
