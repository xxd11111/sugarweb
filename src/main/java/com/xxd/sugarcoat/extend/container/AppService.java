package com.xxd.sugarcoat.extend.container;


/**
 * @author xxd
 * @description TODO
 * @date 2022-11-25
 */
public interface AppService {

    void loadApp(App app);

    void loadAppByNet(String url);

    void loadAppByLocal(String path);

    void loadAppByMarket(String markId);

    void startApp(String id);

    void stopApp(String id);

    void updateApp(String id);

}
