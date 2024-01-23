package com.xxd.orm.datapermission;

import org.hibernate.MappingException;
import org.hibernate.binder.AttributeBinder;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.mapping.*;
import org.hibernate.type.BasicType;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.spi.TypeConfiguration;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * 数据权限逻辑绑定
 *
 * @author 许向东
 * @version 1.0
 */
public class DataPermissionBinder implements AttributeBinder<DataPermission> {

    public static final String FILTER_NAME = "_dataPermission";
    public static final String PARAMETER_NAME = "dataPermission";

    @Override
    public void bind(
            DataPermission dataPermission,
            MetadataBuildingContext buildingContext,
            PersistentClass persistentClass,
            Property property) {
        final InFlightMetadataCollector collector = buildingContext.getMetadataCollector();
        final TypeConfiguration typeConfiguration = collector.getTypeConfiguration();

        final String returnedClassName = property.getReturnedClassName();
        final BasicType<Object> tenantIdType = typeConfiguration
                .getBasicTypeRegistry()
                .getRegisteredType(returnedClassName);

        final FilterDefinition filterDefinition = collector.getFilterDefinition(FILTER_NAME);
        if (filterDefinition == null) {
            collector.addFilterDefinition(
                    new FilterDefinition(
                            FILTER_NAME,
                            "",
                            singletonMap(PARAMETER_NAME, tenantIdType)
                    )
            );
        } else {
            final JavaType<?> tenantIdTypeJtd = tenantIdType.getJavaTypeDescriptor();
            final JavaType<?> parameterJtd = filterDefinition
                    .getParameterJdbcMapping(PARAMETER_NAME)
                    .getJavaTypeDescriptor();
            if (!parameterJtd.getJavaTypeClass().equals(tenantIdTypeJtd.getJavaTypeClass())) {
                throw new MappingException(
                        "all @DataPermission fields must have the same type: "
                                + parameterJtd.getJavaType().getTypeName()
                                + " differs from "
                                + tenantIdTypeJtd.getJavaType().getTypeName()
                );
            }
        }
        persistentClass.addFilter(
                FILTER_NAME,
                columnNameOrFormula(property)
                        + " in(:"
                        + PARAMETER_NAME
                        + ")",
                true,
                emptyMap(),
                emptyMap()
        );

        property.resetUpdateable(false);
        property.resetOptional(false);

    }

    private String columnNameOrFormula(Property property) {
        if (property.getColumnSpan() != 1) {
            throw new MappingException("@DataPermission attribute must be mapped to a single column or formula");
        }
        Selectable selectable = property.getSelectables().get(0);
        return selectable.isFormula()
                ? ((Formula) selectable).getFormula()
                : ((Column) selectable).getName();
    }

}