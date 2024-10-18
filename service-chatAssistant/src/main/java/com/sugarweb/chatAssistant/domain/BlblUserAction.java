package com.sugarweb.chatAssistant.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户操作记录
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class BlblUserAction {

    @TableId
    private String actionId;

    private String actionType;

    private String blblUid;

    private String username;

    private String formatContent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
