package com.sugarweb.digitalHuman.constans;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 文档来源类型
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum DocSourceType {

    /**
     * 文件上传
     */
    FILE_UPLOAD("1"),

    /**
     * 手动输入
     */
    MANUAL_INPUT("2");

    private final String value;

}
