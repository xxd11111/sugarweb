package com.sugarcoat.support.orm.datapermission;

import com.sugarcoat.support.orm.softdelete.SoftDelete;
import org.hibernate.MappingException;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.mapping.*;
import org.hibernate.tuple.AttributeBinder;

/**
 * 数据权限逻辑绑定
 *
 * @author 许向东
 * @date 2023/11/15
 */
public class DataPermissionBinder implements AttributeBinder<SoftDelete> {

    public static final String FILTER_NAME = "_deleteFlag";
    public static final String PARAMETER_NAME = "deleteFlag";
    private String sqlDeleteTemplate = "UPDATE {} SET {} WHERE {} = ? ";
    private String deleteValue = "1";
    private String unDeleteValue = "0";

    @Override
    public void bind(
            SoftDelete softDelete,
            MetadataBuildingContext buildingContext,
            PersistentClass persistentClass,
            Property property) {

    }

    private String columnNameOrFormula(Property property) {
        if (property.getColumnSpan() != 1) {
            throw new MappingException("@SoftDelete attribute must be mapped to a single column or formula");
        }
        Selectable selectable = property.getSelectables().get(0);
        return selectable.isFormula()
                ? ((Formula) selectable).getFormula()
                : ((Column) selectable).getName();
    }

}