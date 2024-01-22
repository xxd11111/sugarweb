package com.xxd.orm.softdelete;

import com.google.common.base.Strings;
import org.hibernate.MappingException;
import org.hibernate.binder.AttributeBinder;
import org.hibernate.boot.model.CustomSql;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
import org.hibernate.mapping.*;

/**
 * SoftDelete逻辑绑定
 *
 * @author 许向东
 * @date 2023/11/15
 */
public class SoftDeleteBinder implements AttributeBinder<SoftDelete> {

    public SoftDeleteBinder() {
        this.deleteValue = "1";
        this.unDeleteValue = "0";
        this.sqlDeleteTemplate = "UPDATE %s SET %s WHERE %s = ? ";
    }

    private final String sqlDeleteTemplate;
    private final String deleteValue;
    private final String unDeleteValue;

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
        String deleteSql = Strings.lenientFormat(sqlDeleteTemplate, tableName, setSql, idColumnName);

        persistentClass.setCustomSqlDelete(new CustomSql(
                deleteSql,
                false,
                ExecuteUpdateResultCheckStyle.NONE)
        );

        String selectWhere = columnName + " = '" + unDeleteValue + "'";
        if (persistentClass instanceof RootClass rootClass) {
            String where = rootClass.getWhere();
            if (Strings.isNullOrEmpty(where)) {
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