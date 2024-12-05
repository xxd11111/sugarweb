package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 场景信息
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Script extends BaseEntity {

    @TableId
    private String scriptId;

    private String scriptName;

    private String description;

    private String promptTemplate;

    @TableField(exist = false)
    private List<ScriptNode> scriptNodeList;

}
