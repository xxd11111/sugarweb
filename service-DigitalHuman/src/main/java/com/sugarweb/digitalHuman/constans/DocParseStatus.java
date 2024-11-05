package com.sugarweb.digitalHuman.constans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 文档解析状态
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum DocParseStatus {
    NOT_PARSED("1"),
    PARSING("2"),
    PARSED("3"),
    FAILED("4");

    private final String value;

}
