package com.xxd.sugarcoat.extend.uims.domain.model.api;

import com.xxd.sugarcoat.support.status.AccessibleEnum;
import lombok.Data;

/**
 * @author xxd
 * @description TODO
 * @date 2022-10-25
 */
@Data
public class Api {

    private String id;

    private String name;

    private String code;

    private String url;

    private String methodType;

    private String remark;

    private AccessibleEnum accessible;

}
