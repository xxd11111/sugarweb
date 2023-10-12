package com.sugarcoat.support.server.domain;

import com.sugarcoat.protocol.server.ApiInfo;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
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
public class SgcApi implements ApiInfo,Cloneable {

	@Id
	private String operationId;

	private String url;

	private String requestMethod;

	private String summary;

	private String operationDescription;

	private String tagName;

	private String tagDescription;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		SgcApi sgcApi = (SgcApi) o;
		return getOperationId() != null && Objects.equals(getOperationId(), sgcApi.getOperationId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public SgcApi clone() {
		try {
			return (SgcApi) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

}
