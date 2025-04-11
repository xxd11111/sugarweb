package com.sugarweb.digitalHuman.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 智能体
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Agent extends BaseEntity {

    @TableId
    private String agentId;

    private String agentName;

    private String agentScript;

}
