package com.sugarcoat.uims.application;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.MenuDto;
import com.sugarcoat.uims.application.vo.MenuTreeVo;
import com.sugarcoat.uims.application.dto.MenuQueryDto;

import java.util.List;

/**
 * 菜单服务
 *
 * @author xxd
 * @date 2022-12-29
 */
public interface MenuService {

    String save(MenuDto menuDTO);

    void modify(MenuDto menuDTO);

    void remove(String id);

    PageData<MenuTreeVo> page(MenuQueryDto menuQueryDTOVO);

    MenuDto find(String id);

    void associateApi(String id, String apiCode);

}
