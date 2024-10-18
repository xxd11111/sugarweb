package com.sugarweb.chatAssistant.constans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BlblUserActionType
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum BlblUserActionType {
    ENTER_ROOM("1"),
    DAN_MU("2"),
    GIFT("3"),
    LIKE("4"),
    GUAN_ZHU("5");

    private final String code;

}
