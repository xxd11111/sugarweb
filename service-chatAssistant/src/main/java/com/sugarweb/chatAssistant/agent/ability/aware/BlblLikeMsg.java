package com.sugarweb.chatAssistant.agent.ability.aware;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * BlblMsg
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlblLikeMsg {

    private String blblUid;

    private String username;

    private String likeNum;

    private LocalDateTime time;

}
