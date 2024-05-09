package com.sugarweb.uims.domain;

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

    private String id;

    private String username;

    private String email;

    private String mobilePhone;

    private String nickName;

    private String salt;

    private String password;

    private String status;

    public void checkCertificate(String certificate) {
        // todo 加密方式问题
        boolean equals = certificate.equals(password);
        if (!equals) {
            throw new SecurityException("凭证错误");
        }
    }

    public void modifyPassword(String newPassword) {
        // todo 加密方式问题
        password = newPassword;
    }

}
