package com.sugarweb.chatAssistant;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.utils.GeneratorUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        // 获取包下的所有类名称
        for (Class<?> clazz : classes) {
            String sql = GeneratorUtil.generateSql(clazz);
            sqlStr.append("\n").append(sql);
        }
        System.out.println(sqlStr);

        String poPackageName = "com.sugarweb.chatAssistant.domain";
        String mapperPackageName = "com.sugarweb.chatAssistant.mapper";
        String writePath = "C:\\xxd-work\\java-project\\sugarcoat\\service-chatAssistant\\src\\main\\java\\com\\sugarweb\\chatAssistant\\domain\\mapper";
        GeneratorUtil.generateMapper(poPackageName, mapperPackageName, writePath);
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
