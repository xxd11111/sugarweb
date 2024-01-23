package com.xxd.orm.datapermission;

import org.hibernate.PropertyValueException;
import org.hibernate.annotations.TenantId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

/**
 * 数据权限自动赋值
 *
 * @author 许向东
 * @version 1.0
 */
public class DataPermissionGeneration implements BeforeExecutionGenerator {

    @TenantId
    private String entityName;
    private String propertyName;

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        String currentOrgId = DataPermissionContext.getDataPermissionInfo().getOrgId();
        if ( o != null ) {
            if (DataPermissionContext.getDataPermissionInfo().isRoot()) {
                // the "root" orgId is allowed to set the org id explicitly
                return o;
            }
            if ( !o.equals(currentOrgId) ) {
                throw new PropertyValueException(
                        "assigned org id differs from current org id: "
                                + o + "!=" + currentOrgId,
                        entityName, propertyName
                );
            }
        }
        return currentOrgId;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return null;
    }
}
