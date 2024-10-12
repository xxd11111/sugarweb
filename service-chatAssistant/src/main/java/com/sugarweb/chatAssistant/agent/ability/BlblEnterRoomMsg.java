package com.sugarweb.chatAssistant.agent.ability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 进入房间消息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlblEnterRoomMsg {

    private String blblUid;

    private String username;

    private LocalDateTime time;

}
