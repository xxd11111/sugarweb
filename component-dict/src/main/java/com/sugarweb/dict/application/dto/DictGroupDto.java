package com.sugarweb.dict.application.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 字典信息
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class DictGroupDto {

    @Size(max = 32)
    private String groupId;

    @Size(max = 32)
    private String groupName;

    @Size(max = 32)
    private String groupCode;

    @Size(max = 1)
    private String groupStatus;

}
