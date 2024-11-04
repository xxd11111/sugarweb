package com.sugarweb.digitalHuman.application.dto;

import com.sugarweb.framework.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * KbPageQuery
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KbPageQuery extends PageQuery {

    private String kbName;

}
