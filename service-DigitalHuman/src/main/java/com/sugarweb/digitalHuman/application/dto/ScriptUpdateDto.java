package com.sugarweb.digitalHuman.application.dto;

import lombok.Data;

/**
 * 场景更新传输对象
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ScriptUpdateDto {

    private String scriptId;

    private String scriptName;

    private String description;

}
