package com.sugarcoat.support.orm.softdelete;

import cn.hutool.core.util.StrUtil;
import org.hibernate.Session;
import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;

/**
 * softDelete自动生成
 *
 * @author 许向东
 * @date 2023/11/15
 */
public class SoftDeleteGeneration implements AnnotationValueGeneration<SoftDelete>, ValueGenerator<Object> {

    private final String unDeleteValue = "0";

    @Override
    public void initialize(SoftDelete annotation, Class<?> propertyType, String entityName, String propertyName) {
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
            return unDeleteValue;
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
