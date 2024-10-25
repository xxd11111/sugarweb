package com.sugarweb.chatAssistant.infra.llm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ModelPlatform
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum ModelPlatform {

    OLLAM("OLLAMA"),
    ZHI_PU("ZHI_PU"),
    TONG_YI("TONG_YI")

    ;

    private final String code;

}
