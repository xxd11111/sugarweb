package com.sugarweb.uims.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;


/**
 * 角色
 *
 * @author xxd
 * @version 1.0
 */
@Data
public class Role {

    @TableId
    private String id;

    private String roleCode;

    private String roleName;

    private String enabled;

}
