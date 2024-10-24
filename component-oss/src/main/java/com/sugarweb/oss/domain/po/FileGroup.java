package com.sugarweb.oss.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 文件组
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class FileGroup {

    @TableId
    private String groupId;

    private String groupCode;

    private String groupName;

    private String maxFileSize;

    private String allowFileType;

    private String denyFileType;

}
