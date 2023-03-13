package com.xxd.sugarcoat.support.server;

import com.xxd.sugarcoat.support.status.AccessibleEnum;
import lombok.Data;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-25
 */
@Data
public class ServerApi {

    private String id;

    private String name;

    private String code;

    private String url;

    private String methodType;

    private String remark;

    private AccessibleEnum accessible;

}
