package com.sugarweb.digitalHuman.constants;

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

    SYSTEM("1"),

    USER("2"),

    ASSISTANT("3");


    private final String value;

}
