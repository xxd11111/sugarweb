package com.sugarcoat.orm;

import com.sugarcoat.protocol.common.BooleanFlag;
import com.sugarcoat.protocol.common.EnumUtil;
import com.sugarcoat.protocol.common.Flag;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.Serializable;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/26
 */
@Converter
public class FlagConvert implements AttributeConverter<BooleanFlag, String> {

    public FlagConvert() {
        System.out.println();
    }

    @Override
    public String convertToDatabaseColumn(BooleanFlag attribute) {
        return attribute.getCode();
    }

    @Override
    public BooleanFlag convertToEntityAttribute(String dbData) {
        return EnumUtil.getByCode(BooleanFlag.class, dbData);
    }

}
