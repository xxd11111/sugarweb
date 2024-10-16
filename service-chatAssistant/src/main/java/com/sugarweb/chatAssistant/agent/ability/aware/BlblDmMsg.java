package com.sugarweb.chatAssistant.agent.ability.aware;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 弹幕信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlblDmMsg {

    private String blblUid;

    private String username;

    private String content;

    private LocalDateTime time;

}
