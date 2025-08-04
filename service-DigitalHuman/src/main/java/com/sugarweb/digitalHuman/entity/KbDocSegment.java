package com.sugarweb.digitalHuman.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 片段
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KbDocSegment extends BaseEntity {

    @TableId
    private String segmentId;

    private String docId;

    private String kbId;

    private String vectorId;

    private String content;

    private String enabled;

}
