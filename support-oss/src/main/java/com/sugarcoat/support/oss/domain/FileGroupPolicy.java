package com.sugarcoat.support.oss.domain;

import lombok.Data;

import java.util.List;

/**
 * 文件组上传策略 todo
 *
 * @author xxd
 * @since 2023/8/3 0:04
 */
@Data
public class FileGroupPolicy {

    private String fileGroup;

    private long maxFileSize;

    private List<String> allowFileTypes;

    private List<String> denyFileTypes;

    private long uploadMaxSize;

}
