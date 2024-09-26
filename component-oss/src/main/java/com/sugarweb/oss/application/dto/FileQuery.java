package com.sugarweb.oss.application.dto;

import lombok.Data;

import java.util.List;

/**
 * 文件指令
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class FileQuery {

	private List<String> fileGroups;

	private String filename;

}
