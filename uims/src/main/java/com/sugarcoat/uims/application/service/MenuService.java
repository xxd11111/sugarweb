package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.MenuDTO;
import com.sugarcoat.uims.application.dto.MenuPageDTO;
import com.sugarcoat.uims.application.dto.MenuQueryVO;

import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-29
 */
public interface MenuService {

    String save(MenuDTO menuDTO);

    void modify(MenuDTO menuDTO);

    void remove(String id);

    PageData<MenuPageDTO> page(MenuQueryVO menuQueryVO);

    MenuDTO find(String id);

    void associateApi(List<String> apis);

}
