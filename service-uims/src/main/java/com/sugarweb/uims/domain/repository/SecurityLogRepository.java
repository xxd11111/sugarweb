package com.sugarweb.uims.domain.repository;


import com.sugarweb.framework.orm.BaseRepository;
import com.sugarweb.uims.domain.SecurityLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * SecurityLogRepository
 *
 * @author 许向东
 * @version 1.0
 */
@Mapper
public interface SecurityLogRepository extends BaseRepository<SecurityLog> {

}
