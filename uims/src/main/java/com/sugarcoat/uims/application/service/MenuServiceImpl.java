package com.sugarcoat.uims.application.service;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarcoat.api.common.PageData;
import com.sugarcoat.api.common.PageDataAdaptManager;
import com.sugarcoat.api.exception.ValidateException;
import com.sugarcoat.orm.ExpressionWrapper;
import com.sugarcoat.support.server.serverApi.ServerApi;
import com.sugarcoat.support.server.serverApi.ServerApiRepository;
import com.sugarcoat.uims.application.dto.MenuDto;
import com.sugarcoat.uims.application.dto.MenuTreeVo;
import com.sugarcoat.uims.application.dto.MenuQueryDto;
import com.sugarcoat.uims.domain.menu.Menu;
import com.sugarcoat.uims.domain.menu.MenuRepository;
import com.sugarcoat.uims.domain.menu.QMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单服务实现类
 *
 * @author xxd
 * @date 2023/6/27 23:15
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final ServerApiRepository serverApiRepository;

    @Override
    public String save(MenuDto menuDto) {
        Menu menu = MenuMapper.INSTANCE.menuDtoToMenu(menuDto);
        menuRepository.save(menu);
        return menu.getId();
    }

    @Override
    public void modify(MenuDto menuDto) {
        Menu menu = menuRepository.findById(menuDto.getId())
                .orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + menuDto.getId()));
        MenuMapper.INSTANCE.updateMenu(menuDto, menu);
        menuRepository.save(menu);
    }

    @Override
    public void remove(String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public PageData<MenuTreeVo> page(MenuQueryDto dto) {
        BooleanExpression booleanExpression = ExpressionWrapper.of()
                .and(StrUtil.isNotBlank(dto.getMenuCode()), QMenu.menu.menuCode.like(dto.getMenuCode(), '/'))
                .and(StrUtil.isNotBlank(dto.getMenuCode()), QMenu.menu.menuName.like(dto.getMenuName(), '/'))
                .and(StrUtil.isNotBlank(dto.getMenuCode()), QMenu.menu.menuCode.eq(dto.getEnable().getCode()))
                .build();

        Page<MenuTreeVo> page = menuRepository.findAll(booleanExpression, PageRequest.of(1, 10))
                .map(MenuMapper.INSTANCE::menuToMenuTreeVo);
        return PageDataAdaptManager.convert(page, MenuTreeVo.class);
    }

    @Override
    public MenuDto find(String id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + id));
        return MenuMapper.INSTANCE.menuToMenuDto(menu);
    }

    @Override
    public void associateApi(String id, List<String> apis) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + id));
        Set<ServerApi> serverApis = new HashSet<>();
        serverApiRepository.findAllById(apis).forEach(serverApis::add);
        menu.setServerApis(serverApis);
        menuRepository.save(menu);
    }
}
