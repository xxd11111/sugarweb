package com.sugarcoat.support.oss.domain;

import com.sugarcoat.api.oss.BizFile;
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
public class SgcBizFile implements BizFile {

    /**
     * 主键
     */
    @Size(max = 32)
    @Id
    private String fileId;

    private String bizId;

    /**
     * 文件组
     */
    @Size(max = 32)
    private String fileGroup;


}