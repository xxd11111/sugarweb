package com.sugarweb.chatAssistant.application;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.chatAssistant.domain.AgentInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * AgentService
 *
 * @author xxd
 * @since 2024/10/15 22:40
 */
@Service
public class AgentService {

    public AgentInfo getAgentInfo(String agentId) {
        return Db.getById(agentId, AgentInfo.class);
    }

    public AgentInfo save(AgentInfo agentInfo) {
        Db.save(agentInfo);
        return agentInfo;
    }

    public AgentInfo update(AgentInfo agentInfo) {
        Db.updateById(agentInfo);
        return agentInfo;
    }

    public AgentInfo defaultAgentInfo() {
        AgentInfo agentInfo = getAgentInfo("1");
        if (agentInfo != null) {
            return agentInfo;
        } else {
            agentInfo = new AgentInfo();
            agentInfo.setAgentId("1");
            agentInfo.setAgentName("炫妹");
            agentInfo.setCreateTime(LocalDateTime.now());
            agentInfo.setUpdateTime(LocalDateTime.now());
            return save(agentInfo);
        }
    }

}
