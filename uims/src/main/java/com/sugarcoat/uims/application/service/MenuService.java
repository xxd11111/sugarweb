package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.MenuDTO;
import com.sugarcoat.uims.application.dto.MenuTreeVO;
import com.sugarcoat.uims.application.dto.MenuQueryDTO;

import java.util.List;

/**
 * 菜单服务
 *
 * @author xxd
 * @date 2022-12-29
 */
public interface MenuService {

    String save(MenuDTO menuDTO);

    void modify(MenuDTO menuDTO);

    void remove(String id);

    PageData<MenuTreeVO> page(MenuQueryDTO menuQueryDTOVO);

    MenuDTO find(String id);

    void associateApi(List<String> apis);

}
