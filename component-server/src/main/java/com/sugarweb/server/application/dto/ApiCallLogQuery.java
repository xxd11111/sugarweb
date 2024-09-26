package com.sugarweb.server.application.dto;

import lombok.Data;

/**
 * 访问日志查询指令
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class ApiCallLogQuery {
    private String apiName;
    private String username;
    private String requestIp;
}
