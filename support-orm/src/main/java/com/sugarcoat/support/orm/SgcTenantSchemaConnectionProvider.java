package com.sugarcoat.support.orm;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * schema 切换
 *
 * @author 许向东
 * @date 2023/11/1
 */
public class SgcTenantSchemaConnectionProvider implements MultiTenantConnectionProvider, HibernatePropertiesCustomizer {

    private final DataSource dataSource;

    public SgcTenantSchemaConnectionProvider(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        connection.createStatement().execute(String.format("use %s;", tenantIdentifier));
        return connection;
    }


    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.createStatement().execute("use public;");
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }

}
