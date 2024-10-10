package com.sugarweb.chatAssistant.temp;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
@Builder
public class BaseMsg {

    private String uid;

    private String username;

    private String msgType;

    private String content;

    private LocalDateTime time;

    private float msgWeight;

}
