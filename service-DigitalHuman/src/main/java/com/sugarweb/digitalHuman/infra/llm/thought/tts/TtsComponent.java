package com.sugarweb.digitalHuman.infra.llm.thought.tts;

import com.sugarweb.digitalHuman.infra.llm.output.OutputContainer;
import com.sugarweb.digitalHuman.infra.llm.output.OutputContent;
import com.sugarweb.digitalHuman.infra.tts.ChatTtsModel;
import com.sugarweb.digitalHuman.infra.tts.TtsModel;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.*;

/**
 * SpeakOutputAbility
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class TtsComponent {

    private final ExecutorService executor;

    private Future<?> ttsThread = null;

    private final BlockingQueue<OutputContent> audioPlayList = new LinkedBlockingQueue<>();

    private final OutputContainer outputContainer;

    /**
     * 线程池最大线程数
     */
    private final int maxThread = 2;

    //todo 根据配置文件动态配置
    private final TtsModel ttsModel = new ChatTtsModel("http://127.0.0.1:9966/tts");

    public TtsComponent(ExecutorService executor, OutputContainer outputContainer) {
        this.executor = executor;
        this.outputContainer = outputContainer;
    }

    public void putAudio(OutputContent outputContent) throws InterruptedException {
        audioPlayList.put(outputContent);
    }

    public void start() {
        if (ttsThread != null && !ttsThread.isDone()) {
            return;
        }

        ttsThread = executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    //顺序并行处理
                    int size = outputContainer.size();
                    //实际并行数量
                    int parallelNum = Math.min(maxThread, size);
                    CountDownLatch countDownLatch = new CountDownLatch(parallelNum);
                    for (int i = 0; i < parallelNum; i++) {
                        //控制顺序
                        OutputContent outputContent = outputContainer.take();
                        Future<String> filePath = executor.submit(() -> {
                            try {
                                return ttsModel.tts(outputContent.getContent());
                            } finally {
                                //finish
                                countDownLatch.countDown();
                            }
                        });
                        outputContent.setFilePath(filePath);
                        putAudio(outputContent);
                    }
                    //如果此时并行数量大于2，则等待
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    log.error("ttsTask InterruptedException", e);
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public void stop() {
        if (ttsThread == null || ttsThread.isDone()) {
            return;
        }
        ttsThread.cancel(true);
    }

}
