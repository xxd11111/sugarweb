package com.sugarweb.chatAssistant.temp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseMsg {

    private String uid;

    private String username;

    private String msgType;

    private String content;

    private LocalDateTime time;

}
