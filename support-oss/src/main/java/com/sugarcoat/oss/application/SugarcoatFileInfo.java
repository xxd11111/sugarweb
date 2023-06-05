package com.sugarcoat.oss.application;

import com.sugarcoat.oss.api.FileInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 文件信息
 */
@Entity
@Getter
@Setter
@ToString
public class SugarcoatFileInfo implements FileInfo {

    /**
     * 主键
     */
    @Size(max = 32)
    @Id
    private String fileId;

    /**
     * 路径
     */
    @Size(max = 100)
    private String fileUrl;

    /**
     * 文件名
     */
    @Size(max = 50)
    private String fileName;

    /**
     * 文件类型
     */
    @Size(max = 1)
    private String fileType;

    /**
     * 文件大小
     */
    @Size(max = 32)
    private String fileSize;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 业务表主键
     */
    @Size(max = 32)
    private String associationId;

    /**
     * 附件所属业务类型
     */
    @Size(max = 32)
    private String fileGroup;
}