package com.sugarweb.digitalHuman.component.tts;

import cn.hutool.core.util.StrUtil;
import com.sugarweb.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Slf4j
public class ChatTtsModel implements TtsModel {

    private ChatTtsClient chatTtsClient;

    public ChatTtsModel(String url) {
        chatTtsClient = new ChatTtsClient(url);
    }

    /**
     * 文本转语音
     * 这是个耗时的方法
     *
     * @param content 文本
     * @return 音频文件路径
     */
    @Override
    public String tts(String content) {
        // todo 解决[uv_break]问题 https://github.com/jianchang512/ChatTTS-ui/issues/240
        log.info("未清洗数据 content: {}", content);
        // 只允许使用汉字，句号，逗号，感叹号；其他的替换为空白；
        //将特殊符合处理
        content = StrUtil.replace(content, "?", "。");
        content = StrUtil.replace(content, ";", ",");
        content = StrUtil.replace(content, ":", ",");
        content = StrUtil.replace(content, "？", "。");
        content = StrUtil.replace(content, "、", ",");
        //正则表达式
        content = StrUtil.replace(content, "[^\\u4e00-\\u9fa5\\u3002\\uFF0C\\uFF1B\\uFF01]", "");
        log.info("清洗数据后 content: {}", content);

        if (StrUtil.isBlank(content)) {
            throw new IllegalArgumentException("content is blank");
        }
        TtsResponse tts = chatTtsClient.tts(TtsRequest.builder()
                .voice("1031.pt")
                .text(content)
                .build());
        if (!tts.success()) {
            log.error("ChatTtsClient error: {}", tts.getMsg());
            throw new ServerException(tts.getMsg());
        }
        List<TtsAudioFile> audioFiles = tts.getAudio_files();
        if (audioFiles == null || audioFiles.isEmpty()) {
            log.error("ChatTtsClient error: audioFiles is empty or null");
            throw new ServerException("ChatTtsClient error: audioFiles is empty or null");
        }
        TtsAudioFile first = audioFiles.getFirst();
        String filename = first.getFilename();
        if (StrUtil.isBlank(filename)) {
            log.error("ChatTtsClient error: filename is empty");
            throw new ServerException("ChatTtsClient error: filename is empty");
        }
        return filename;
    }
}
