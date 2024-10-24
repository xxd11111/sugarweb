package com.sugarweb.chatAssistant.application.dto;

import com.sugarweb.framework.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 代理分页查询条件
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AgentPageQuery extends PageQuery {

    private String agentName;

}
