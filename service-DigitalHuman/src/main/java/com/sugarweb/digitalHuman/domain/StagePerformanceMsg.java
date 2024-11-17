package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * PerformanceConversation
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StagePerformanceMsg extends BaseEntity {

    @TableId
    private String msgId;

    private String msgPid;

    private String performanceId;

    private String stageId;

    private String modelId;

    private String actorId;

    private String systemMsg;

    private String question;

    private String answer;

    private String historyMsg;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    /**
     * 单位：毫秒
     */
    private Integer costTime;


    /**
     * 0:用户消息，1:系统消息
     */
    private String msgType;

    private String userId;

}
