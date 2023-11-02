package com.sugarcoat.support.orm;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 获取多数据源
 *
 * @author xxd
 * @since 2023/11/3 0:04
 */
public class PropertiesLoadDataSource {

    public DataSource getDefaultDataSource(){
        return null;
    }

    public Map<Object, Object> getDynamicDataSource(){
        return null;
    }

}
