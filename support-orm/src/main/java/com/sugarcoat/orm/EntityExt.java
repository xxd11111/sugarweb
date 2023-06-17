package com.sugarcoat.orm;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 实体类信息补充，新增，更新
 *
 * @author xxd
 * @date 2022-11-12
 */
@Getter
@Setter
@ToString
// @MappedSuperclass
public class EntityExt {

	@CreatedBy
	@Column(length = 32)
	private String createBy;

	@CreatedDate
	private LocalDateTime createDate;

	@LastModifiedBy
	@Column(length = 32)
	private String updateBy;

	@LastModifiedDate
	private LocalDateTime updateDate;

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
