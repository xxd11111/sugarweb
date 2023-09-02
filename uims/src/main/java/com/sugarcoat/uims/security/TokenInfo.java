package com.sugarcoat.uims.security;

import lombok.Data;

/**
 * 凭证信息
 *
 * @author xxd
 * @version 1.0
 * @since 2023/7/5
 */
@Data
public class TokenInfo {

    String sessionId;

    String userId;

    String ip;

    String mac;

}
