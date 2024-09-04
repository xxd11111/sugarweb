package com.sugarweb.uims.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

    @TableId
    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String salt;

    private String password;

    private String status;

}
