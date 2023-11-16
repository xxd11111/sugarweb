package com.sugarcoat.uims.domain;

import com.sugarcoat.support.orm.SgcRepository;
import com.sugarcoat.support.orm.datapermission.DataPermissionFilter;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/26
 */
@DataPermissionFilter
public interface DemoRepo extends SgcRepository<DemoDo> {

}
