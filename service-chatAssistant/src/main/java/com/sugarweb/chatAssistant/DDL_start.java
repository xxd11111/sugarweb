package com.sugarweb.chatAssistant;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.utils.DDL_Generator;

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
        Set<Class<?>> classes = ClassUtil.scanPackage("com.sugarweb", a -> StrUtil.contains(a.getName(), "domain.po."));
        StringBuilder sqlStr = new StringBuilder();
        // 获取包下的所有类名称
        for (Class<?> clazz : classes) {
            String sql = DDL_Generator.generateSql(clazz);
            sqlStr.append("\n").append(sql);
        }

        generateMapper();

    }

    public static void generateMapper() {
        String mapperTemplate = """
                package {mapperPackageName};
                
                import com.baomidou.mybatisplus.core.mapper.BaseMapper;
                import {poPackageName}.{poClassName};
                
                /**
                 * {poClassName}
                 *
                 * @author xxd
                 * @since {date}
                 */
                public interface {poClassName}Mapper extends BaseMapper<{poClassName}> {
                
                }
                """;
        String mapperPackageName = "com.sugarweb.chatAssistant.domain.mapper";
        String poPackageName = "com.sugarweb.chatAssistant.domain.po";
        String writePath = "C:\\xxd-work\\java-project\\sugarcoat\\service-chatAssistant\\src\\main\\java\\com\\sugarweb\\chatAssistant\\domain\\mapper";
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        Set<Class<?>> classes = ClassUtil.scanPackage(poPackageName);
        // 获取包下的所有类名称
        for (Class<?> clazz : classes) {
            String poClassName = clazz.getSimpleName();
            String content = StrUtil.replace(mapperTemplate, "{poClassName}", poClassName)
                    .replace("{poPackageName}", poPackageName)
                    .replace("{mapperPackageName}", mapperPackageName)
                    .replace("{date}", date);
            FileUtil.writeString(content, writePath + "\\" + poClassName + "Mapper.java", "UTF-8");
        }
    }


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
