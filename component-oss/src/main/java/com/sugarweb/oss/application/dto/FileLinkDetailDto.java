package com.sugarweb.oss.application.dto;

import lombok.Data;

/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class FileLinkDetailDto {

    private String linkId;

    private String bizId;

    private String fileId;

    private String groupCode;

    private FileDetailDto fileDetail;

}
