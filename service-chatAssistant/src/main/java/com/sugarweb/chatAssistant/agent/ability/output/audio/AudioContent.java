package com.sugarweb.chatAssistant.agent.ability.output.audio;

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
public class AudioContent {

    private long thinkId;

    private int splitId;

    private String content;

    private Future<String> filePath;

}
