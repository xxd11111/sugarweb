package com.sugarweb.chatAssistant.agent.ability.input.blbl;

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
public class BlblGiftMsg {

    private String blblUid;

    private String username;

    private String gifyName;

    private String giftPrice;

    private String giftNum;

    private LocalDateTime time;

}
