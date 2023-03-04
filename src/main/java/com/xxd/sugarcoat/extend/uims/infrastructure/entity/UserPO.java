package com.xxd.sugarcoat.extend.uims.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxd.sugarcoat.extend.uims.Common;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-12
 */

@Getter
@Setter
@TableName(Common.TABLE_PREFIX + "user")
public class UserPO {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String username;

    private String password;

    private String salt;

    private String nickName;

    private String mobilePhone;

    private String email;

    private String status;
}
