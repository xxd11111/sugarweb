package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ScriptTopic
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScriptNode extends BaseEntity {

    @TableId
    private String nodeId;

    private String nodeName;

    private String nodePid;

    private String nodeIndex;

    private String scriptId;

    private String scriptContent;

}
