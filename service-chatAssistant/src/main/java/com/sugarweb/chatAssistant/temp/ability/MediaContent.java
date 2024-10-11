package com.sugarweb.chatAssistant.temp.ability;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Future;

/**
 * 媒体内容
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaContent {

    private String content;

    private long chatId;

    private int splitId;

    private Future<String> filePath;

}
