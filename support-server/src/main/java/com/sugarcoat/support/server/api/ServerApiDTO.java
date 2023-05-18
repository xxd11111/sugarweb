package com.sugarcoat.support.server.api;

import lombok.Data;


/**
 * TODO
 *
 * @author xxd
 * @version 1.0
 * @date 2023/5/8
 */
@Data
public class ServerApiDTO {
    private String id;

    private String name;

    private String code;

    private String url;

    private String methodType;

    private String remark;

    private String status;
}
