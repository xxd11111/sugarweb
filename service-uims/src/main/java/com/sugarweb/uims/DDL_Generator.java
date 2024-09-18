package com.sugarweb.uims;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 根据实体类生成表生成语句
 */
@Slf4j
class DDL_Generator {
    /**
     * java类型与数据库类型映射
     */
    public static HashMap<String, String> javaPropertyMap = new HashMap<>(16, 1);

    static {
        javaPropertyMap.put("integer", " int(8) ");
        javaPropertyMap.put("int", " int(8) ");
        javaPropertyMap.put("short", " tinyint(4) ");
        javaPropertyMap.put("byte", " tinyint(4) ");
        javaPropertyMap.put("long", " bigint(11) ");
        javaPropertyMap.put("bigdecimal", " decimal(20,4) ");
        javaPropertyMap.put("double", " double(10,2) ");
        javaPropertyMap.put("float", " float(10,2) ");
        javaPropertyMap.put("boolean", " tinyint(4) ");
        javaPropertyMap.put("timestamp", " datetime ");
        javaPropertyMap.put("date", " datetime ");
        javaPropertyMap.put("localdatetime", " datetime ");
        javaPropertyMap.put("string", " varchar(255) ");
    }

    public static void main(String[] args) {
        StringBuilder sqlStr1 = buildSql();
        System.out.println(sqlStr1);
//        sqlToFile(sqlStr1.toString(), filePath + "work.sql");
    }

    private static StringBuilder buildSql() {
        Set<Class<?>> classes = ClassUtil.scanPackage("com.sugarweb", a -> StrUtil.contains(a.getName(), "domain"));
        StringBuilder sqlStr = new StringBuilder();
        // 获取包下的所有类名称
        for (Class<?> str : classes) {
            try {
                String className = str.getName();
                String sql = generateSql(className);
                sqlStr.append("\n ").append(sql);
            } catch (Exception e) {
                log.error("错误类:{}", str);
            }
        }
        return sqlStr;
    }

    /**
     * 根据实体类生成建表语句
     *
     * @param className 全类名
     */
    public static String generateSql(String className) {
        //注意mysql自增需要数值类型，不能用string类型
        try {
            Class<?> clz = Class.forName(className);
            String className1 = dealSubLine(clz.getSimpleName());
            //处理className
            className = className1.substring(1);
            //获取表中文名称
            String tableCommit = "";
            String tableCommitVarchar = " COMMENT='" + tableCommit + "';";
            Field[] fields = clz.getDeclaredFields();
            StringBuilder column = new StringBuilder();
            String primaryKey = null;
            List<String> colList = new ArrayList<>();
            for (Field f : fields) {
                String dealName;
                if (!"serialVersionUID".equals(f.getName())) {
                    //获取注解名称
                    String commitVachr = " COMMENT '',";
                    //驼峰转换
                    dealName = dealSubLine(f.getName());
                    if (colList.contains(dealName)) {
                        continue;
                    }
                    String filedTypeName = f.getType().getName();
                    String suffix = filedTypeName.substring(filedTypeName.lastIndexOf(".") + 1);
                    if ("List".equals(suffix)) {
                        continue;
                    }
                    String dataType = javaPropertyMap.get(suffix.toLowerCase());
                    if (StrUtil.isBlank(dataType)) {
                        log.error("字段类型为空：{}", suffix);
                        continue;
                    }

                    TableId tableId = f.getAnnotation(TableId.class);
                    if (Objects.nonNull(tableId)) {
                        column.append(" \n `").append(dealName).append("`").append(dataType).append("NOT NULL ").append(commitVachr);
                        primaryKey = dealName;
                    } else {
                        column.append(" \n `").append(dealName).append("`").append(dataType).append(" DEFAULT NULL ").append(commitVachr);
                    }
                    colList.add(dealName);
                }
            }
            return "\n DROP TABLE IF EXISTS `" + className + "`; " +
                    " \n CREATE TABLE `" + className + "`  (" + " \n " + column +
                    " \n PRIMARY KEY (`" + primaryKey + "`) " +
                    " \n ) ENGINE = InnoDB  CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci " +
                    tableCommitVarchar;
        } catch (ClassNotFoundException e) {
            log.debug("该类未找到！");
            return null;
        }

    }

    /**
     * 将string 写入sql文件
     *
     * @param str  sql
     * @param path sql打印路径
     */
    public static void writeFile(String str, String path) {
        byte[] sourceByte = str.getBytes();
        try {
            //文件路径（路径+文件名）
            File file = new File(path);
            //文件不存在则创建文件，先创建目录
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            //文件输出流用于将数据写入文件
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(sourceByte);
            outStream.flush();
            //关闭文件输出流
            outStream.close();
            System.out.println("生成成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String dealSubLine(String str) {
        //String str = "areaObjectCode";
        StringBuilder ss = new StringBuilder();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] >= 'A' && charArray[i] <= 'Z') {
                ss.append("_").append(charArray[i]);
            } else {
                ss.append(charArray[i]);
            }
        }
        return ss.toString().toLowerCase();
    }

}
