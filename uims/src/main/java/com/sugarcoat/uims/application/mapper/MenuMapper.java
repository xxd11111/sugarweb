package com.sugarcoat.uims.application.mapper;

import com.sugarcoat.uims.application.dto.MenuDto;
import com.sugarcoat.uims.application.vo.MenuTreeVo;
import com.sugarcoat.uims.domain.menu.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 菜单mapper
 *
 * @author xxd
 * @since 2023/6/29 22:32
 */
@Mapper
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    @Mapping(source = "id", target = "pid")
    @Mapping(source = "id", target = "menuCode")
    @Mapping(source = "id", target = "menuName")
    Menu menuDtoToMenu(MenuDto menuDTO);

    void updateMenu(MenuDto menuDTO, @MappingTarget Menu menu);

    MenuDto menuToMenuDto(Menu menu);

    MenuTreeVo menuToMenuTreeVo(Menu menu);
}
