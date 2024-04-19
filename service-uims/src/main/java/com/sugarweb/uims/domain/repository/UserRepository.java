package com.sugarweb.uims.domain.repository;

import com.sugarweb.framework.orm.BaseRepository;
import com.sugarweb.uims.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户仓库
 *
 * @author xxd
 * @version 1.0
 */
@Mapper
public interface UserRepository extends BaseRepository<User> {

}
