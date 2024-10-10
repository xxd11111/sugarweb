package com.sugarweb.chatAssistant.temp;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.sugarweb.chatAssistant.infra.*;
import com.sugarweb.chatAssistant.temp.ability.BlblMsgAwareAbility;
import com.sugarweb.chatAssistant.temp.ability.GoalSelector;
import com.sugarweb.chatAssistant.temp.ability.SpeakAbility;
import com.sugarweb.chatAssistant.temp.ability.TopicSelector;

import java.util.List;

/**
 * 自动智能助手
 *
 * @author xxd
 * @version 1.0
 */
public class AutoAgent {

    boolean stop = false;

    private SpeakAbility speakAbility;

    /**
     * 主题选择器
     */
    private TopicSelector topicSelector;

    /**
     * 达成目标选择器
     */
    private GoalSelector goalSelector;

    /**
     * 消息收集器
     */
    private BlblMsgAwareAbility blblMsgAwareAbility;

    /**
     * 消息权重计算器
     */
    private MsgWeightCalculator msgWeightCalculator;

    /**
     * 多用户rag管道
     */
    private MultiuserRagPipeline multiuserRagPipeline;

    public void start() {
        long lastTime = System.currentTimeMillis();
        while (true) {
            // 每100ms 思考一次
            long now = System.currentTimeMillis();
            if (now - lastTime < 100) {
                ThreadUtil.sleep(100);
            }
            lastTime = now;
            try {
                if (stop) {
                    return;
                }
                think();
            } catch (Exception e) {
                //异常处理
            }
        }
    }

    public void stop() {
        stop = true;
    }


    public void think() {
        //todo 获取主题（事件主线）
        String topic = topicSelector.currentTopic();
        //获取目标
        String currentGoal = goalSelector.currentGoal();

        //与观众互动
        List<BaseMsg> msgList = blblMsgAwareAbility.getMsg();
        // todo 互动优先级
        // 1.送礼互动
        // 2.弹幕互动
        // 3.进房间互动
        // 4.自由发挥，扯淡互动

        //组装消息
        multiuserRagPipeline.chat(msgList, speakAbility::addSpeakStreamContent, a -> speakAbility.streamSubmit());

    }

    private StringBuffer output = new StringBuffer();

    public void think(String token) {
        output.append(token);
    }

    public void speak(String speakContent) {

        ChatTtsClient chatTtsClient = new ChatTtsClient();
        TtsResponse tts = chatTtsClient.tts(TtsRequest.builder()
                .text(speakContent)
                .build());
        if (!tts.success()) {
            return;
        }
        List<TtsAudioFile> audioFiles = tts.getAudio_files();
        if (CollUtil.isNotEmpty(audioFiles)) {
            VlcUtil.playAudio(audioFiles.getFirst().getFilename());
        }
    }


}
