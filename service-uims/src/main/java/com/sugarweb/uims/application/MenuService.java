package com.sugarweb.uims.application;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.uims.domain.dto.MenuDto;
import com.sugarweb.uims.domain.dto.MenuTreeVo;
import com.sugarweb.uims.domain.dto.MenuQueryDto;

/**
 * 菜单服务
 *
 * @author xxd
 * @version 1.0
 */
public interface MenuService {

    String save(MenuDto dto);

    void modify(MenuDto dto);

    void remove(String id);

    IPage<MenuTreeVo> page(PageQuery pageQuery, MenuQueryDto dto);

    MenuDto find(String id);

}
