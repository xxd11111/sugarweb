package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
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
public class DatasetDocumentSegment extends BaseEntity {

    @TableId
    private String segmentId;

    private String documentId;

    private String datasetId;

    private String vectorId;

    private String content;

    private Integer position;

    private String status;

}
