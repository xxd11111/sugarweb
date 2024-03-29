package com.sugarweb.uims.application.impl;

import com.google.common.base.Strings;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sugarweb.framework.common.PageData;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.application.dto.MenuDto;
import com.sugarweb.uims.application.vo.MenuTreeVo;
import com.sugarweb.uims.application.dto.MenuQueryDto;
import com.sugarweb.uims.application.MenuService;
import com.sugarweb.uims.domain.menu.Menu;
import com.sugarweb.uims.domain.menu.MenuRepository;
import com.sugarweb.uims.domain.menu.QMenu;
import com.sugarweb.framework.orm.ExpressionWrapper;
import com.sugarweb.framework.orm.PageDataConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


/**
 * 菜单服务实现类
 *
 * @author xxd
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public String save(MenuDto menuDto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        menuRepository.save(menu);
        return menu.getId();
    }

    @Override
    public void modify(MenuDto menuDto) {
        Menu menu = menuRepository.findById(menuDto.getId())
                .orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + menuDto.getId()));
        BeanUtils.copyProperties(menuDto, menu);
        menuRepository.save(menu);
    }

    @Override
    public void remove(String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public PageData<MenuTreeVo> page(MenuQueryDto dto) {
        BooleanExpression booleanExpression = ExpressionWrapper.of()
                .and(!Strings.isNullOrEmpty(dto.getMenuCode()), QMenu.menu.menuCode.like(dto.getMenuCode(), '/'))
                .and(!Strings.isNullOrEmpty(dto.getMenuCode()), QMenu.menu.menuName.like(dto.getMenuName(), '/'))
                .and(!Strings.isNullOrEmpty(dto.getMenuCode()), QMenu.menu.enable.eq(dto.getEnable()))
                .build();

        Page<MenuTreeVo> page = menuRepository.findAll(booleanExpression, PageRequest.of(1, 10))
                .map(a->{
                    MenuTreeVo target = new MenuTreeVo();
                    BeanUtils.copyProperties(a, target);
                    return target;
                });
        return PageDataConvert.convert(page, MenuTreeVo.class);
    }

    @Override
    public MenuDto find(String id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + id));
        MenuDto target = new MenuDto();
        BeanUtils.copyProperties(menu, target);
        return target;
    }

}
