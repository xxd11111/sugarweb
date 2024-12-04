package com.sugarweb.digitalHuman.infra.llm.input;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 输入内容
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class InputContent {

    private String userId;

    private String username;

    private String content;

    private LocalDateTime createTime;

}
