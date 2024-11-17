package com.sugarweb.digitalHuman.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 代理详情
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ActorDetailDto {

    private String actorId;

    private String actorName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
