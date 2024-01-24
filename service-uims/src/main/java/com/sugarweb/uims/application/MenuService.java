package com.sugarweb.uims.application;

import com.sugarweb.common.PageData;
import com.sugarweb.uims.application.dto.MenuDto;
import com.sugarweb.uims.application.vo.MenuTreeVo;
import com.sugarweb.uims.application.dto.MenuQueryDto;

/**
 * 菜单服务
 *
 * @author xxd
 * @version 1.0
 */
public interface MenuService {

    String save(MenuDto menuDTO);

    void modify(MenuDto menuDTO);

    void remove(String id);

    PageData<MenuTreeVo> page(MenuQueryDto menuQueryDTOVO);

    MenuDto find(String id);

}
