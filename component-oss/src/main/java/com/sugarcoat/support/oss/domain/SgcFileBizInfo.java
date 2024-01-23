package com.sugarcoat.support.oss.domain;

import com.sugarcoat.support.oss.api.FileBizInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件信息
 */
@Entity
@Getter
@Setter
@ToString
public class SgcFileBizInfo implements FileBizInfo {

    @Id
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
     * 业务类型
     */
    @Size(max = 32)
    private String bizType;

}