package com.sugarcoat.uims.application.service;

import com.sugarcoat.api.common.PageData;
import com.sugarcoat.uims.application.dto.MenuDTO;
import com.sugarcoat.uims.application.dto.MenuTreeVO;
import com.sugarcoat.uims.application.dto.MenuQueryDTO;
import com.sugarcoat.uims.domain.menu.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单服务实现类
 *
 * @author xxd
 * @date 2023/6/27 23:15
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;

    @Override
    public String save(MenuDTO menuDTO) {
        return null;
    }

    @Override
    public void modify(MenuDTO menuDTO) {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public PageData<MenuTreeVO> page(MenuQueryDTO menuQueryDTOVO) {
        return null;
    }

    @Override
    public MenuDTO find(String id) {
        return null;
    }

    @Override
    public void associateApi(List<String> apis) {

    }
}
