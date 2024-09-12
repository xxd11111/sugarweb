package com.sugarweb.framework.orm;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 实体类信息补充，新增，更新
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class BaseEntity {

	private String createBy;

	private LocalDateTime createDate;

	private String lastModifiedBy;

	private LocalDateTime lastModifiedDate;

	
	public int hashCode() {
		return getClass().hashCode();
	}

}
