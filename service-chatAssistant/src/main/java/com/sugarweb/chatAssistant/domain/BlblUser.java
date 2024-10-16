package com.sugarweb.chatAssistant.domain;

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

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
