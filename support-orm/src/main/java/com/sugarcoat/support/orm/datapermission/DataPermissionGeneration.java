package com.sugarcoat.support.orm.datapermission;

import cn.hutool.core.util.StrUtil;
import com.sugarcoat.support.orm.softdelete.SoftDelete;
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
public class DataPermissionGeneration implements AnnotationValueGeneration<SoftDelete>, ValueGenerator<Object> {

    private String entityName;
    private String propertyName;

    @Override
    public void initialize(SoftDelete annotation, Class<?> propertyType, String entityName, String propertyName) {
        this.entityName = entityName;
        this.propertyName = propertyName;
    }

    @Override
    public void initialize(SoftDelete annotation, Class<?> propertyType) {
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
        if (StrUtil.isEmpty((String) currentValue)) {
            return "0";
        }
        return currentValue;
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
