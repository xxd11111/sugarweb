package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import com.sugarweb.oss.domain.po.FileInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Document
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DatasetDocument extends BaseEntity {

    @TableId
    private String documentId;

    private String datasetId;

    private String documentName;

    @TableField(exist = false)
    private FileInfo fileInfo;

    // 手动创建，文件上传
    private String sourceType;

    private String status;

    private String parseStatus;

    private String errorMsg;

    private Integer segmentCount;

}
