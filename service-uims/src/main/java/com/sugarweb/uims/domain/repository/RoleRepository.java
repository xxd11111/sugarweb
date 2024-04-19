package com.sugarweb.uims.domain.repository;


import com.sugarweb.framework.orm.BaseRepository;
import com.sugarweb.uims.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色仓库
 *
 * @author xxd
 * @version 1.0
 */
@Mapper
public interface RoleRepository extends BaseRepository<Role> {

}
