package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识库信息
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Dataset extends BaseEntity {

    @TableId
    private String datasetId;

    private String datasetName;

    private String collectionName;

    private Integer dimension;

    private String embeddingModelId;

    private String embeddingModelName;

    /**
     * 模型信息
     */
    @TableField(exist = false)
    private Model embeddingModel;

    private String status;

    private String description;

}
