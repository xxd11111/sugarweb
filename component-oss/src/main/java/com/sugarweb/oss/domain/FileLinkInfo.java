package com.sugarweb.oss.domain;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件信息
 */
@Getter
@Setter
@ToString
public class FileLinkInfo {

    @Size(max = 32)
    private String id;

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
    private String fileGroup;

}