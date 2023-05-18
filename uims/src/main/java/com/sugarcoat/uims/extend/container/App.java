package com.sugarcoat.uims.extend.container;

import io.undertow.Undertow;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-29
 */
public class App {

    private String id;
    private String appName;
    private String appUri;
    private String storageType;


    public static void main(String[] args) {
        for(int i = 8080; i < 8081; i++){
            int finalI = i;
            Undertow.builder()
                    .addHttpListener(i, "localhost")
                    .setHandler(httpServerExchange -> httpServerExchange.getResponseSender().send("hello:"+ finalI)).build().start();
        }

    }
}
