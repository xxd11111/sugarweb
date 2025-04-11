package com.sugarweb.digitalHuman.component.llm.thought;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.digitalHuman.component.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.component.llm.output.OutputContent;
import com.sugarweb.digitalHuman.component.tts.ChatTtsModel;
import com.sugarweb.digitalHuman.component.tts.TtsModel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

/**
 * TtsThoughtListener
 * 非线程安全
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class TtsOutputListener implements StreamThoughtListener {

    private final OutputContainer outputContainer;
    private final StringBuilder sb = new StringBuilder();
    private int currentSplitId = 0;

    // 限制同时进行tts的线程数,公平锁，先入先出
    private final Semaphore maxTtsThread = new Semaphore(2, true);
    //todo 获取tts模型
    private final TtsModel ttsModel = new ChatTtsModel("http://127.0.0.1:9966/tts");
    //虚拟线程
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public TtsOutputListener(OutputContainer outputContainer) {
        this.outputContainer = outputContainer;
    }

    @Override
    public void onNext(ThoughtRequest thoughtRequest, String token) {
        if (StrUtil.isBlank(token)) {
            return;
        }
        if (sb.length() + token.length() > 20) {
            handleTokenWithPunctuation(token, thoughtRequest.getThoughtId());
        } else {
            sb.append(token);
        }
    }

    private void handleTokenWithPunctuation(String token, long thinkId) {
        String[] punctuationMarks = {"。", "！", "!", "？", "?"};
        for (String mark : punctuationMarks) {
            if (token.contains(mark)) {
                String[] split = token.split(mark, 2); // 限制分割次数为2，避免创建过多数组
                if (split.length > 1) {
                    sb.append(split[0]).append(mark);
                    addOutputContent(thinkId, currentSplitId++, sb.toString());
                    sb.setLength(0); // 清空StringBuilder
                    sb.append(split[1]);
                    return;
                }
                // 只分割一次
                break;
            }
        }
        sb.append(token);
    }

    private void addOutputContent(long thinkId, int spiltId, String content) {
        if (StrUtil.isBlank(content)) {
            return;
        }
        //异步处理
        Future<String> filePath = executor.submit(() -> {
            try {
                maxTtsThread.acquire();
                return ttsModel.tts(content);
            } finally {
                maxTtsThread.release();
            }
        });
        outputContainer.add(OutputContent.builder()
                .thoughtId(thinkId)
                .splitId(spiltId)
                .content(content)
                .filePath(filePath)
                .build());
    }

    @Override
    public void onComplete(ThoughtRequest thoughtRequest) {
        addOutputContent(thoughtRequest.getThoughtId(), currentSplitId++, sb.toString());
        sb.setLength(0);
        currentSplitId = 0;
    }

    @Override
    public void onError(ThoughtRequest thoughtRequest, Throwable error) {
        sb.setLength(0);
        currentSplitId = 0;
    }


}
