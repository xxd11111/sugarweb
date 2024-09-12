package com.sugarweb.oss.application;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * FileDto
 *
 * @author 许向东
 * @version 1.0
 */
@Data
@Builder
public class FileDto {

    /**
     * 主键
     */
    @Size(max = 32)
    private String fileId;

    /**
     * 文件组
     */
    @Size(max = 32)
    private String groupCode;

    /**
     * 路径
     */
    @Size(max = 100)
    private String fileKey;

    /**
     * 文件全名
     */
    @Size(max = 50)
    private String filename;

    /**
     * 文件类型
     */
    @Size(max = 1)
    private String fileType;

    /**
     * 内容类型
     */
    @Size(max = 50)
    private String contentType;

    /**
     * 文件大小
     */
    @Size(max = 32)
    private long fileSize;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

}
