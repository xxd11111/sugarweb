package com.sugarweb.digitalHuman.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.sugarweb.digitalHuman.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户操作记录
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlblUserActionLog extends BaseEntity {

    @TableId
    private String actionId;

    private String actionType;

    private String blblUid;

    private String username;

    private String formatContent;

}
