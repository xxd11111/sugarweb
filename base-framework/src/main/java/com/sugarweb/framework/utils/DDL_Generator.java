package com.sugarweb.framework.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 根据实体类生成表生成语句
 */
@Slf4j
public class DDL_Generator {

    /**
     * 是否使用`
     * 使用
     * create table `dict_item` (
     * `column1` varchar(255) not null comment '',
     * )
     * 不使用
     * create table dict_item (
     * column1 varchar(255) not null comment '',
     * )
     */
    public static boolean useSurround = true;

    /**
     * java类型与数据库类型映射
     */
    public static HashMap<String, String> javaToJdbcMap = new HashMap<>(16, 1);

    static {
        javaToJdbcMap.put("integer", "int(8)");
        javaToJdbcMap.put("int", "int(8)");
        javaToJdbcMap.put("short", "tinyint(4)");
        javaToJdbcMap.put("byte", "tinyint(4)");
        javaToJdbcMap.put("long", "bigint(11)");
        javaToJdbcMap.put("bigdecimal", "decimal(20,4)");
        javaToJdbcMap.put("double", "double(10,2)");
        javaToJdbcMap.put("float", "float(10,2)");
        javaToJdbcMap.put("boolean", "tinyint(4)");
        javaToJdbcMap.put("timestamp", "datetime");
        javaToJdbcMap.put("date", "datetime");
        javaToJdbcMap.put("localdatetime", "datetime");
        javaToJdbcMap.put("string", "varchar(255)");
    }

    //排除的字段
    private static final List<String> exculudeList = List.of("serialVersionUID");


    private static String getColumnSurround() {
        return useSurround ? "`" : "";
    }

    private static String getFieldComment(Field field) {
        String result = Optional.ofNullable(field)
                .map(f -> f.getAnnotation(Schema.class))
                .map(Schema::description)
                .orElse("");
        return surround(result, "'");
    }

    private static String getClassComment(Class<?> clazz) {
        String result = Optional.ofNullable(clazz)
                .map(f -> f.getAnnotation(Schema.class))
                .map(Schema::description)
                .orElse("");
        return surround(result, "'");
    }

    private static String getTableName(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("class is null");
        }
        String result = Optional.of(clazz)
                .map(f -> f.getAnnotation(TableName.class))
                .map(TableName::value)
                .filter(StrUtil::isNotEmpty)
                .orElse(humpToUnderline(clazz.getSimpleName()));
        return surround(result, getColumnSurround());
    }

    private static String getColumnName(Field field) {
        if (field == null) {
            throw new IllegalArgumentException("field is null");
        }
        String result = Optional.of(field)
                .map(f -> f.getAnnotation(TableId.class))
                .map(TableId::value)
                .filter(StrUtil::isNotEmpty)
                .orElse(Optional.of(field)
                        .map(f -> f.getAnnotation(TableField.class))
                        .filter(TableField::exist)
                        .map(TableField::value)
                        .filter(StrUtil::isNotEmpty)
                        .orElse(humpToUnderline(field.getName()))
                );
        return surround(result, getColumnSurround());
    }

    private static boolean isPrimaryKey(Field field) {
        return field.getAnnotation(TableId.class) != null;
    }

    private static boolean nullable(Field field) {
        return !isPrimaryKey(field);
    }

    private static String surround(String str, String ch) {
        return ch + str + ch;
    }

    /**
     * 根据实体类生成建表语句
     */
    public static String generateSql(Class<?> clazz) {
        //注意mysql自增需要数值类型，不能用string类型
        String tableName = getTableName(clazz);
        //表备注
        String tableComment = getClassComment(clazz);

        StringBuilder columnStr = new StringBuilder();
        String primaryKey = null;
        boolean isFirst = true;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (exculudeList.contains(field.getName())) {
                continue;
            }
            //根据字段名称，获取数据库字段名称
            String filedTypeName = field.getType().getName();
            String suffix = filedTypeName.substring(filedTypeName.lastIndexOf(".") + 1);
            if ("List".equals(suffix)) {
                continue;
            }

            String columnComment = getFieldComment(field);
            //获取备注
            String columnCommentStr = "comment " + columnComment;

            //获取字段名称
            String columnName = getColumnName(field);
            if (isPrimaryKey(field)) {
                primaryKey = columnName;
            }
            //获取字段类型
            String dataType = javaToJdbcMap.get(suffix.toLowerCase());
            if (StrUtil.isBlank(dataType)) {
                log.error("字段类型未匹配：{}", suffix);
                continue;
            }
            //获取字段是否允许为空
            String nullableStr = nullable(field) ? "default null" : "not null";

            //拼接字段
            if (!isFirst) {
                columnStr.append(",");
                columnStr.append("\n");
            }
            columnStr.append(columnName).append(" ").append(dataType).append(" ").append(nullableStr).append(" ").append(columnCommentStr);
            isFirst = false;
        }
        if (StrUtil.isNotEmpty(primaryKey)) {
            columnStr.append(",\nPRIMARY KEY (").append(primaryKey).append(") ");
        }

        String dropTemp = """
                drop table if exists {tableName};
                """;
        String createTemp = """
                create table {tableName} (
                {columnStr}
                ) engine = innodb comment = {tableComment};
                 """;
        String replace = StrUtil.replace(dropTemp + createTemp, "{tableName}", tableName);
        replace = StrUtil.replace(replace, "{columnStr}", columnStr.toString());
        replace = StrUtil.replace(replace, "{tableComment}", tableComment);
        return replace;

    }

    /**
     * 将string 写入sql文件
     *
     * @param str  sql
     * @param path sql打印路径
     */
    public static void writeFile(String str, String path) {
        FileUtil.writeUtf8String(str, path);
    }

    /**
     * 将下划线转驼峰
     */
    public static String underlineToHump(String str) {
        return StrUtil.toCamelCase(str, '_');
    }

    /**
     * 将驼峰转下划线
     */
    public static String humpToUnderline(String str) {
        return StrUtil.toSymbolCase(str, '_');
    }

}
