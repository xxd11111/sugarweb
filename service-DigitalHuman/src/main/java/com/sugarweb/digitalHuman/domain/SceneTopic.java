package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * SceneTopic
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class SceneTopic {

    @TableId
    private String topicId;

    private String topicName;

    private String topicIndex;

    private String topicContent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
