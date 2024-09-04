package com.sugarweb.uims.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Collection;

/**
 * 角色
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role {

    @TableId
    private String id;

    private String roleCode;

    private String roleName;

    @TableField(exist = false)
    private Collection<Menu> menus;

    private String status;

}
