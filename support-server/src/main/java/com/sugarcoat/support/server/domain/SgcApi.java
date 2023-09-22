package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.ApiInfo;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * 服务接口
 *
 * @author xxd
 * @since 2022-10-25
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class SgcApi implements ApiInfo {

	@Id
	private String id;

	private String code;

	private String name;

	private String url;

	private String methodType;

	private String remark;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		SgcApi sgcApi = (SgcApi) o;
		return getId() != null && Objects.equals(getId(), sgcApi.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
