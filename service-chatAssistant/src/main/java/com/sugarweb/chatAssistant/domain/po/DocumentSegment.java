package com.sugarweb.chatAssistant.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 片段
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class DocumentSegment {

    @TableId
    private String segmentId;

    private String segmentContent;

    private String documentId;

    private String syncStatus;

    private String vectorId;

    private String createTime;

    private String updateTime;


}
