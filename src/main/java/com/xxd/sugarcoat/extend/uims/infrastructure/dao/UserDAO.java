package com.xxd.sugarcoat.extend.uims.infrastructure.dao;

import com.xxd.sugarcoat.extend.uims.infrastructure.entity.UserPO;
import com.xxd.sugarcoat.support.orm.api.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxd
 * @description TODO
 * @date 2022-12-16
 */
@Mapper
public interface UserDAO extends BaseDAO<UserPO> {

}
