package com.sugarweb.oss.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 文件信息
 */
@Data
public class FileLink {

    @TableId
    private String linkId;
    /**
     * 文件主键
     */
    @Size(max = 32)
    private String fileId;

    /**
     * 业务id
     */
    @Size(max = 32)
    private String bizId;

    /**
     * 文件组
     */
    @Size(max = 32)
    private String groupCode;

    @TableField(exist = false)
    private FileInfo fileInfo;

}
