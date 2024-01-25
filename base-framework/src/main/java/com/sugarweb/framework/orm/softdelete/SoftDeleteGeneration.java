package com.sugarweb.framework.orm.softdelete;

import com.google.common.base.Strings;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

/**
 * softDelete自动生成
 *
 * @author 许向东
 * @version 1.0
 */
public class SoftDeleteGeneration implements BeforeExecutionGenerator {

    private final String UN_DELETE_VALUE = "0";

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        if (Strings.isNullOrEmpty((String) o)) {
            return UN_DELETE_VALUE;
        }
        return o;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return null;
    }

}
