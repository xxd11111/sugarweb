package com.sugarweb.oss.po;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 文件信息
 */
@Data
public class FileLink {

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

}