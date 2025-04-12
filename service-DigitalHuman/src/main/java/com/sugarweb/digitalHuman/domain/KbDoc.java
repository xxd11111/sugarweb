package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import com.sugarweb.oss.domain.FileInfo;
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
public class KbDoc extends BaseEntity {

    @TableId
    private String docId;

    private String kbId;

    private String docName;

    @TableField(exist = false)
    private FileInfo fileInfo;

    ///来源类型 1：文件上传 2：手动创建
    private String sourceType;

    private String enabled;

    private String parseMethod;

    private String parseConfig;

    private String parseStatus;

    private String parseMsg;

    private Integer segmentCount;

}
