package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * StageInfo
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Stage extends BaseEntity {

    @TableId
    private String stageId;

    private String stageName;

    private String description;

    private String actorId;

    /**
     * 演出脚本id
     */
    private String scriptId;

    /**
     * 0未表演 1表演中
     */
    private String status;

    /**
     * 当前演出id
     */
    private String performanceId;


    /**
     * tts模式
     */
    private String ttsMode;

    /**
     * 直播平台
     */
    private String livePlatform;

    /**
     * 本地输出模式
     */
    private String localOutputMode;

    /**
     * 是否开启websocket模式
     */
    private String websocketMode;

}
