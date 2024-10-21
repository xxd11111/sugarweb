package com.sugarweb.chatAssistant;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.utils.GeneratorUtil;

import java.util.Set;

/**
 * 生成初始化sql脚本
 *
 * @author xxd
 * @since 2024/10/1 11:39
 */
public class DDL_start {

    public static String packageName = "com.sugarweb.chatAssistant.domain.po";

    public static void main(String[] args) {
        Set<Class<?>> classes = ClassUtil.scanPackage("com.sugarweb.chatAssistant.domain");
        StringBuilder sqlStr = new StringBuilder();
        // 获取chatAssistant项目的实体类
        for (Class<?> clazz : classes) {
            String sql = GeneratorUtil.generateSql(clazz);
            sqlStr.append("\n").append(sql);
        }
        System.out.println(sqlStr);

        // 获取各个组件的实体类
        Set<Class<?>> componentClazzSet = ClassUtil.scanPackage("com.sugarweb", a -> StrUtil.contains(a.getName(), "domain.po."));
        StringBuilder componentSql = new StringBuilder();
        // 获取包下的所有类名称
        for (Class<?> clazz : componentClazzSet) {
            String sql = GeneratorUtil.generateSql(clazz);
            componentSql.append("\n").append(sql);
        }
        System.out.println(componentSql);

        // String poPackageName = "com.sugarweb.chatAssistant.domain";
        // String mapperPackageName = "com.sugarweb.chatAssistant.mapper";
        // String writePath = "C:\\xxd-work\\java-project\\sugarcoat\\service-chatAssistant\\src\\main\\java\\com\\sugarweb\\chatAssistant\\domain\\mapper";
        // GeneratorUtil.generateMapper(poPackageName, mapperPackageName, writePath);
    }


    //todo
    public static void generateClass() {
        String templateClass = """
                package {packageName};
                
                import lombok.Data;
                
                /**
                 * {className}
                 *
                 * @author xxd
                 * @since {date}
                 */
                @Data
                public class {className} {
                
                {fields}
                
                }
                """;

        String fieldTemplate = """
                    @Schema(description = "{description}")
                    private {fieldType} {fieldName};
                """;
        String idFieldTemplate = """
                    @TableId
                    @Schema(description = "{description}")
                    private {fieldType} {fieldName};
                """;

        //获取表结构
        String sql = """
                SELECT
                    COLUMN_NAME,
                    DATA_TYPE,
                    IS_NULLABLE,
                    COLUMN_DEFAULT
                FROM
                    INFORMATION_SCHEMA.COLUMNS
                """;
    }


}
