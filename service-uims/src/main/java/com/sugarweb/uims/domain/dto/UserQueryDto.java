package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.BooleanFlag;
import lombok.Data;

/**
 * 用户查询dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserQueryDto {

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private BooleanFlag enable;
}
