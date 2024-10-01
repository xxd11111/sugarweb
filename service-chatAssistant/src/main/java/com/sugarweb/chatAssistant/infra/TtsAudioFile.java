package com.sugarweb.chatAssistant.infra;

import lombok.Data;

/**
 * TtsAudioFile
 *
 * @author xxd
 * @since 2024/9/30 22:40
 */
@Data
public class TtsAudioFile {

    /**
     * 文件存储地址（用于同一个服务器时候）
     */
    private String filename;

    /**
     * 文件下载地址（用于多服务器时候）
     */
    private String url;

}
