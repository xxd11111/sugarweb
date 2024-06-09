package com.sugarweb.uims.domain.dto;

import com.sugarweb.framework.common.Flag;
import lombok.Data;

/**
 * 用户查询dto
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class UserQuery {

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private Flag enable;
}
