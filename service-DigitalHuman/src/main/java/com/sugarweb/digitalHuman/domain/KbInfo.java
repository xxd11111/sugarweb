package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class KbInfo {

    @TableId
    private String kbId;

    private String kbName;

    private String collectionName;

    private String embeddingModelId;

    private String dimension;

    private String status;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
