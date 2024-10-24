package com.sugarweb.chatAssistant.application.dto;

import com.sugarweb.framework.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 场景分页查询参数
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScenePageQuery extends PageQuery {

    private String stageId;

    private String sceneName;

}
