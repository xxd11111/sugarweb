package com.sugarcoat.support.orm.datapermission;

import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**
 * 数据权限自动赋值
 *
 * @author 许向东
 * @date 2023/11/15
 */
public class DataPermissionGeneration implements AnnotationValueGeneration<DataPermission>, ValueGenerator<Object> {

    private String entityName;
    private String propertyName;

    @Override
    public void initialize(DataPermission annotation, Class<?> propertyType, String entityName, String propertyName) {
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public void initialize(DataPermission annotation, Class<?> propertyType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GenerationTiming getGenerationTiming() {
        return GenerationTiming.INSERT;
    }

    @Override
    public ValueGenerator<?> getValueGenerator() {
        return this;
    }

    @Override
    public Object generateValue(Session session, Object owner, Object currentValue) {
        String currentOrgId = DataPermissionContext.getDataPermissionInfo().getOrgId();
        if ( currentValue != null ) {
            if (DataPermissionContext.getDataPermissionInfo().isRoot()) {
                // the "root" orgId is allowed to set the org id explicitly
                return currentValue;
            }
            if ( !currentValue.equals(currentOrgId) ) {
                throw new PropertyValueException(
                        "assigned org id differs from current org id: "
                                + currentValue + "!=" + currentOrgId,
                        entityName, propertyName
                );
            }
        }
        return currentOrgId;
    }

    @Override
    public Object generateValue(Session session, Object owner) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean referenceColumnInSql() {
        return false;
    }

    @Override
    public String getDatabaseGeneratedReferencedColumnValue() {
        return null;
    }
}
