package com.sugarweb.oss.domain;

import lombok.Data;

import java.util.List;

/**
 * 文件组上传策略
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class FileGroupPolicy {

    private String fileGroup;

    private long maxSize;

    private List<String> allowFileTypes;

    private List<String> denyFileTypes;

}
