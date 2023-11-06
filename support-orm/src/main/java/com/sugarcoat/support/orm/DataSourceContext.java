package com.sugarcoat.support.orm;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/11/4
 */
public class DataSourceContext {

    private static final ThreadLocal<String> dsId = ThreadLocal.withInitial(()->"master");

    public static String getDsId(){
        return dsId.get();
    }

    public static void setDsId(String dsId){
        DataSourceContext.dsId.set(dsId);
    }

}
