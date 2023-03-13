package com.xxd.sugarcoat.extend.uims.domain.model.user;

import com.xxd.sugarcoat.extend.uims.infrastructure.entity.UserPO;
import com.xxd.sugarcoat.support.orm.BaseRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-16
 */
@Mapper
public interface UserRepository extends BaseRepository<UserPO> {

}
