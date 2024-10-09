package com.sugarweb.chatAssistant.application;

import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class BaseMsg {

    private String uid;

    private String username;

    private String msgType;

    private String content;

    private float msgWeight;

}
