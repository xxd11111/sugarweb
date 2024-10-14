package com.sugarweb.chatAssistant.agent.ability;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ChatRole
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum ChatRole {

    USER("user"),

    ASSISTANT("assistant"),

    SYSTEM("system");

    private final String code;

}
