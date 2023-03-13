package com.xxd.sugarcoat.support.server;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xxd.sugarcoat.extend.uims.Common;
import lombok.*;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-13
 */

@Getter
@Setter
@TableName(Common.TABLE_PREFIX + "server_api")
public class ServerApiPO {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String apiName;

    private String apiCode;

    private String apiUrl;

    private String methodType;

    private String remark;

    private String status;

}
