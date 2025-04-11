package com.sugarweb.digitalHuman.component.llm.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Future;

/**
 * tts内容封装
 *
 * @author xxd
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutputContent {

    private long thoughtId;

    private int splitId;

    private String content;

    private Future<String> filePath;

}
