package com.sugarweb.oss.po;

import lombok.Data;

/**
 * 文件组
 *
 * @author 许向东
 * @version 1.0
 */
@Data
public class FileGroup {

    private String groupId;

    private String groupCode;

    private String groupName;

    private String maxFileSize;

    private String allowFileType;

    private String denyFileType;

}
