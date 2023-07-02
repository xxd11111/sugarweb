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
 * @date 2023/6/29 22:32
 */
@Mapper
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    @Mapping(target = "serverApis", ignore = true)
    Menu menuDtoToMenu(MenuDto menuDTO);

    @Mapping(target = "serverApis", ignore = true)
    void updateMenu(MenuDto menuDTO, @MappingTarget Menu menu);

    @Mapping(target = "serverApis", ignore = true)
    MenuDto menuToMenuDto(Menu menu);

    @Mapping(target = "serverApis", ignore = true)
    MenuTreeVo menuToMenuTreeVo(Menu menu);
}
