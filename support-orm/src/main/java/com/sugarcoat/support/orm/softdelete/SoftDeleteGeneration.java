package com.sugarcoat.support.orm.softdelete;

import com.google.common.base.Strings;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

/**
 * softDelete自动生成
 *
 * @author 许向东
 * @date 2023/11/15
 */
public class SoftDeleteGeneration implements BeforeExecutionGenerator {

    private final String unDeleteValue = "0";

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        if (Strings.isNullOrEmpty((String) o)) {
            return unDeleteValue;
        }
        return o;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return null;
    }

}
