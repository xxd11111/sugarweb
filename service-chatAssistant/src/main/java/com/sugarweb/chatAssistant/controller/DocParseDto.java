package com.sugarweb.chatAssistant.controller;

import lombok.Data;

import java.util.List;

/**
 * DocParseDto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DocParseDto {

    private List<String> docIds;

    private String parseOperate;

}
