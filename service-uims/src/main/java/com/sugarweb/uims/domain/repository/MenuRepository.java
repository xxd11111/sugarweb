package com.sugarweb.uims.domain.repository;

import com.sugarweb.framework.orm.BaseRepository;
import com.sugarweb.uims.domain.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单仓库
 *
 * @author xxd
 * @version 1.0
 */
@Mapper
public interface MenuRepository extends BaseRepository<Menu> {

}
