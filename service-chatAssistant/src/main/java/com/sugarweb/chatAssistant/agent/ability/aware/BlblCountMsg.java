package com.sugarweb.chatAssistant.agent.ability.aware;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * blbl定时统计信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlblCountMsg {

    private String watchedCount;

    private String watchingCount;

    private String likeCount;

    private LocalDateTime time;

}
