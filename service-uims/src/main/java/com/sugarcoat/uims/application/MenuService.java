package com.sugarcoat.uims.application;

import com.xxd.common.PageData;
import com.sugarcoat.uims.application.dto.MenuDto;
import com.sugarcoat.uims.application.vo.MenuTreeVo;
import com.sugarcoat.uims.application.dto.MenuQueryDto;

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
