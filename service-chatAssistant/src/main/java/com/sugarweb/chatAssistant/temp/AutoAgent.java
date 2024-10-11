package com.sugarweb.chatAssistant.temp;

import cn.hutool.core.thread.ThreadUtil;
import com.sugarweb.chatAssistant.temp.ability.BlblMsgAwareAbility;
import com.sugarweb.chatAssistant.temp.ability.SpeakAbility;
import com.sugarweb.chatAssistant.temp.ability.StreamThinkSpeakAdapt;
import com.sugarweb.chatAssistant.temp.ability.ThinkAbility;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自动智能助手
 *
 * @author xxd
 * @version 1.0
 */
public class AutoAgent {
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private SpeakAbility speakAbility;
    private ThinkAbility thinkAbility;
    private BlblMsgAwareAbility blblMsgAwareAbility;
    private Thread runningThread = null;

    public void init() {
        speakAbility = new SpeakAbility(executor);
        thinkAbility = new ThinkAbility(executor);
        blblMsgAwareAbility = new BlblMsgAwareAbility();
        speakAbility.init();
        blblMsgAwareAbility.init();
    }

    public void start() {
        speakAbility.start();
        blblMsgAwareAbility.start();
        runningThread = Thread.startVirtualThread(() -> {
            long lastTime = System.currentTimeMillis();
            while (!Thread.currentThread().isInterrupted()) {
                // 限制最快100ms 思考一次
                limitThinkSpeed(lastTime, 100);
                lastTime = System.currentTimeMillis();
                long chatId = System.currentTimeMillis();
                // 从消息队列中获取消息
                List<BaseMsg> msg = blblMsgAwareAbility.consumeMsg();
                if (!msg.isEmpty()) {
                    StreamThinkSpeakAdapt streamThinkSpeakAdapt = new StreamThinkSpeakAdapt(chatId, speakAbility);
                    thinkAbility.streamThink(msg, streamThinkSpeakAdapt);
                }
            }
        });
    }

    private void limitThinkSpeed(long lastTime, long limit) {
        long now = System.currentTimeMillis();
        if (now - lastTime < limit) {
            ThreadUtil.sleep(now - lastTime);
        }
    }

    public void stop() {
        if (runningThread != null) {
            runningThread.interrupt();
        }
    }


}
