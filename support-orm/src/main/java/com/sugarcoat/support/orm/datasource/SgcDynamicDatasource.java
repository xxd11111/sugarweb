package com.sugarcoat.support.orm.datasource;

import com.sugarcoat.protocol.exception.FrameworkException;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SgcTenantRoutingDatasource
 *
 * @author 许向东
 * @date 2023/11/1
 */
public class SgcDynamicDatasource extends AbstractRoutingDataSource {

    private final Map<Object, Object> targetDatasourceMap = new ConcurrentHashMap<>();

    public SgcDynamicDatasource(DynamicDataSourceProperties dynamicDataSourceProperties) {
        HikariDataSource defaultDs = DataSourceFactory.create(dynamicDataSourceProperties);
        defaultDs.validate();
        setDefaultTargetDataSource(defaultDs);
        Map<String, SgcDataSourceProperties> dynamic = dynamicDataSourceProperties.getDynamic();
        dynamic.forEach((k, v) -> {
            HikariDataSource hikariDataSource = DataSourceFactory.create(v);
            hikariDataSource.validate();
            targetDatasourceMap.put(k, hikariDataSource);
        });
        setTargetDataSources(targetDatasourceMap);
    }

    public void put(String key, HikariDataSource datasource) {
        datasource.validate();
        Connection connection;
        try {
            connection = datasource.getConnection();
            connection.isValid(1);
            targetDatasourceMap.put(key, datasource);
        } catch (SQLException e) {
            throw new FrameworkException("database connect exception", e);
        }
    }

    public void remove(Object key) {
        Object o = targetDatasourceMap.get(key);
        if (o instanceof HikariDataSource hikariDataSource) {
            hikariDataSource.close();
        }
        targetDatasourceMap.remove(key);
    }

    @Override
    protected String determineCurrentLookupKey() {
        return DataSourceContext.getDsId();
    }

}
