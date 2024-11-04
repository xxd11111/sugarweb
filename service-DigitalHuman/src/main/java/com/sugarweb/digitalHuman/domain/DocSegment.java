package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 片段
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocSegment {

    @TableId
    private String segmentId;

    private String docId;

    private String content;

    private String parseStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
