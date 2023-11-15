package com.sugarcoat.support.orm.softdelete;

import cn.hutool.core.util.StrUtil;
import org.hibernate.MappingException;
import org.hibernate.boot.model.CustomSql;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
import org.hibernate.mapping.*;
import org.hibernate.tuple.AttributeBinder;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/15
 */
public class SoftDeleteBinder implements AttributeBinder<SoftDelete> {

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

        String columnName = columnNameOrFormula(property);
        String setSql = columnName + " = " + deleteValue;
        String tableName = persistentClass.getTable().getName();
        String idColumnName = persistentClass.getIdentifierProperty().getName();
        String deleteSql = StrUtil.format(sqlDeleteTemplate, tableName, setSql, idColumnName);

        persistentClass.setCustomSqlDelete(new CustomSql(
                deleteSql,
                false,
                ExecuteUpdateResultCheckStyle.NONE)
        );

        String selectWhere = columnName + " = '" + unDeleteValue + "'";
        if (persistentClass instanceof RootClass rootClass) {
            String where = rootClass.getWhere();
            if (StrUtil.isEmpty(where)) {
                rootClass.setWhere(selectWhere);
            } else {
                rootClass.setWhere(where + " and " + selectWhere);
            }
        }

        property.resetUpdateable(false);
        property.resetOptional(false);
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