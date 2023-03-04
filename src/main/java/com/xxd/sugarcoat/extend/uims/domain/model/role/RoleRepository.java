package com.xxd.sugarcoat.extend.uims.domain.model.role;

import com.xxd.sugarcoat.extend.uims.infrastructure.entity.RolePO;
import com.xxd.sugarcoat.support.orm.jpa.BaseRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxd
 * @description TODO
 * @date 2023-01-03
 */
@Mapper
public interface RoleRepository extends BaseRepository<RolePO> {
}
