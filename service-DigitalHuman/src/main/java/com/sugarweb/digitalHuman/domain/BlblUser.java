package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * bilibili用户信息
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlblUser extends BaseEntity {

    @TableId
    private String blblUid;

    private String username;

    private String level;

    private String avatar;

    private String remark;

}
