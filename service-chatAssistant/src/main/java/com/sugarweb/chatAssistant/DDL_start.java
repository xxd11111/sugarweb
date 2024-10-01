package com.sugarweb.chatAssistant;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.utils.DDL_Generator;

import java.util.Set;

/**
 * 生成初始化sql脚本
 *
 * @author xxd
 * @since 2024/10/1 11:39
 */
public class DDL_start {
    public static void main(String[] args) {
        Set<Class<?>> classes = ClassUtil.scanPackage("com.sugarweb", a -> StrUtil.contains(a.getName(), "domain.po."));
        StringBuilder sqlStr = new StringBuilder();
        // 获取包下的所有类名称
        for (Class<?> clazz : classes) {
            String sql = DDL_Generator.generateSql(clazz);
            sqlStr.append("\n").append(sql);
        }
        System.out.println(sqlStr);
        // writeFile(sqlStr.toString(), "D:\\temp\\sql.sql");
    }
}
