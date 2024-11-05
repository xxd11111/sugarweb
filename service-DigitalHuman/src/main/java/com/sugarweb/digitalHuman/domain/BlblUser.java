package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * bilibili用户信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class BlblUser {

    @TableId
    private String blblUid;

    private String username;

    /**
     * ai对用户的描述
     */
    private String summary;

    /**
     * 当前场景动作
     */
    private String currentSceneAction;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
