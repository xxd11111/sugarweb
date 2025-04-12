package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
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
public class Kb extends BaseEntity {

    @TableId
    private String kbId;

    private String kbName;

    private String collectionName;

    private Integer dimension;

    private String embeddingModelId;

    private String embeddingModelName;

    private String description;

    private String parseMethod;

    private String parseConfig;

    /**
     * 模型信息
     */
    @TableField(exist = false)
    private Model embeddingModel;
}
