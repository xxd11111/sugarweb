package com.sugarweb.chatAssistant.application;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
public class MindCenter {

    /**
     * 主题选择器
     */
    private TopicSelector topicSelector;

    /**
     * 目标选择器
     */
    private GoalSelector goalSelector;

    /**
     * 消息接收器
     */
    private MsgReceiver msgReceiver;

    /**
     * 消息权重计算器
     */
    private MsgWeightCalculator msgWeightCalculator;

    /**
     * 多用户rag管道
     */
    private MultiuserRagPipeline multiuserRagPipeline;

    public void think(){
        //获取消息
        List<BaseMsg> msgList = msgReceiver.getMsg();
        //获取目标
        String currentGoal = goalSelector.currentGoal();
        for (BaseMsg msg : msgList) {
            //计算权重
            float w = msgWeightCalculator.calculateWeight(msg, currentGoal);
            msg.setMsgWeight(w);
        }
        String assembly = assembly(msgList);

        String chat = multiuserRagPipeline.chat(assembly);
    }

    private String assembly(List<BaseMsg> msgList){
        StringBuilder sb = new StringBuilder();
        for (BaseMsg msg : msgList) {
            sb.append(msg.getContent());
        }
        return sb.toString();
    }


}
