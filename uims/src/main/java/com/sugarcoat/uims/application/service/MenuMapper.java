package com.sugarcoat.uims.application.service;

import com.sugarcoat.uims.application.dto.MenuDto;
import com.sugarcoat.uims.application.dto.MenuTreeVo;
import com.sugarcoat.uims.domain.menu.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 菜单mapper
 *
 * @author xxd
 * @date 2023/6/29 22:32
 */
@Mapper
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    Menu menuDtoToMenu(MenuDto menuDTO);

    void updateMenu(MenuDto menuDTO, @MappingTarget Menu menu);

    MenuDto menuToMenuDto(Menu menu);

    MenuTreeVo menuToMenuTreeVo(Menu menu);
}
