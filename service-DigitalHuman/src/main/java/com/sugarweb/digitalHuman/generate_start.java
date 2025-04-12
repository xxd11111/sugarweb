package com.sugarweb.digitalHuman;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.utils.GeneratorUtil;

import java.util.Set;

/**
 * 生成初始化sql脚本
 * 准备整合到项目中
 *
 * @author xxd
 * @since 2024/10/1 11:39
 */
public class generate_start {

    public static String packageName = "com.sugarweb.chatAssistant.domain.po";

    /**
     * code first 模式生成建表脚本
     */
    public static void main(String[] args) {
        // 生成sql
        // Set<Class<?>> classes = ClassUtil.scanPackage("com.sugarweb.digitalHuman.domain");
        // StringBuilder sqlStr = new StringBuilder();
        // // 获取项目的实体类
        // for (Class<?> clazz : classes) {
        //     String sql = GeneratorUtil.generateSql(clazz);
        //     sqlStr.append("\n").append(sql);
        // }
        // System.out.println(sqlStr);

        // 获取各个组件的实体类
        Set<Class<?>> componentClazzSet = ClassUtil.scanPackage("com.sugarweb", a -> StrUtil.contains(a.getName(), "domain."));
        StringBuilder componentSql = new StringBuilder();
        // 获取包下的所有类名称
        for (Class<?> clazz : componentClazzSet) {
            String sql = GeneratorUtil.generateSql(clazz);
            componentSql.append("\n").append(sql);
        }
        System.out.println(componentSql);

        // 生成mapper.java
        // String poPackageName = "com.sugarweb.digitalHuman.domain";
        // String mapperPackageName = "com.sugarweb.digitalHuman.mapper";
        // String writePath = "C:\\xxd-work\\java-project\\sugarcoat\\service-DigitalHuman\\src\\main\\java\\com\\sugarweb\\digitalHuman\\mapper";
        // GeneratorUtil.generateMapper(poPackageName, mapperPackageName, writePath);
    }


    //todo  根据sql 生成实体类
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
