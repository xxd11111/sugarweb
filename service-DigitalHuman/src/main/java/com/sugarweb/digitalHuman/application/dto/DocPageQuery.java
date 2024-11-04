package com.sugarweb.digitalHuman.application.dto;

import com.sugarweb.framework.common.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * DocPageQuery
 *
 * @author xxd
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocPageQuery extends PageQuery {

    private String docName;

}
