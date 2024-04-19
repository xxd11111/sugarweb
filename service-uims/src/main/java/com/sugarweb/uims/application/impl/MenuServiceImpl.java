package com.sugarweb.uims.application.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.common.PageUtil;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.domain.dto.MenuDto;
import com.sugarweb.uims.domain.dto.MenuTreeVo;
import com.sugarweb.uims.domain.dto.MenuQueryDto;
import com.sugarweb.uims.application.MenuService;
import com.sugarweb.uims.domain.Menu;
import com.sugarweb.uims.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Menu menu = Optional.ofNullable(menuRepository.selectById(menuDto.getId()))
                .orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + menuDto.getId()));
        BeanUtils.copyProperties(menuDto, menu);
        menuRepository.save(menu);
    }

    @Override
    public void remove(String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public IPage<MenuTreeVo> page(PageQuery pageQuery, MenuQueryDto dto) {
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<Menu>()
                .like(Menu::getMenuName, dto.getMenuName())
                .eq(Menu::getMenuCode, dto.getMenuCode())
                .eq(Menu::getEnable, dto.getEnable());

        return menuRepository.selectPage(PageUtil.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a->{
                    MenuTreeVo target = new MenuTreeVo();
                    BeanUtils.copyProperties(a, target);
                    return target;
                });
    }

    @Override
    public MenuDto find(String id) {
        Menu menu = Optional.ofNullable(menuRepository.selectById(id))
                .orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + id));
        MenuDto target = new MenuDto();
        BeanUtils.copyProperties(menu, target);
        return target;
    }

}
