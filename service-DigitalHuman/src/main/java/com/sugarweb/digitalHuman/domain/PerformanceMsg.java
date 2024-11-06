package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * PerformanceConversation
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class PerformanceMsg {

    @TableId
    private String msgId;

    private String performanceId;

    private String question;

    private String message;

    private String answer;

    private String modelName;

    private String modelPlatform;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double costTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
