package com.sugarweb.uims.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sugarweb.framework.common.PageQuery;
import com.sugarweb.framework.orm.PageHelper;
import com.sugarweb.framework.exception.ValidateException;
import com.sugarweb.uims.application.dto.MenuDto;
import com.sugarweb.uims.application.dto.MenuTreeVo;
import com.sugarweb.uims.application.dto.MenuQueryDto;
import com.sugarweb.uims.domain.Menu;
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
public class MenuService {

    public String save(MenuDto menuDto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        Db.save(menu);
        return menu.getId();
    }

    public void modify(MenuDto menuDto) {
        Menu menu = Optional.ofNullable(Db.getById(menuDto.getId(), Menu.class))
                .orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + menuDto.getId()));
        BeanUtils.copyProperties(menuDto, menu);
        Db.save(menu);
    }

    public void remove(String id) {
        Db.removeById(id);
    }

    public IPage<MenuTreeVo> page(PageQuery pageQuery, MenuQueryDto dto) {
        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<Menu>()
                .like(Menu::getMenuName, dto.getMenuName())
                .eq(Menu::getMenuCode, dto.getMenuCode())
                .eq(Menu::getStatus, dto.getEnable());

        return Db.page(PageHelper.getPage(pageQuery), lambdaQueryWrapper)
                .convert(a -> {
                    MenuTreeVo target = new MenuTreeVo();
                    BeanUtils.copyProperties(a, target);
                    return target;
                });
    }

    public MenuDto find(String id) {
        Menu menu = Optional.ofNullable(Db.getById(id, Menu.class))
                .orElseThrow(() -> new ValidateException("菜单不存在,id:{}" + id));
        MenuDto target = new MenuDto();
        BeanUtils.copyProperties(menu, target);
        return target;
    }

}
