package com.sugarweb.digitalHuman.component.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 模型类型
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum ModelType {

    CHAT("chat"),
    EMBEDDING("embedding"),
    TTS("tts"),
    ;

    private final String value;

}
