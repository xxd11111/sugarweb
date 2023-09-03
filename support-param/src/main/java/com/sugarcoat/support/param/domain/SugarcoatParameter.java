package com.sugarcoat.support.param.domain;

import com.sugarcoat.orm.EntityExt;
import com.sugarcoat.api.param.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * 参数实体
 *
 * @author xxd
 * @since 2022-11-18
 */
@Entity
@Getter
@Setter
@ToString
public class SugarcoatParameter extends EntityExt implements Parameter {

	@Id
	private String id;

	private String code;

	private String name;

	private String value;

	private String comment;

	private String defaultValue;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
			return false;
		SugarcoatParameter sugarcoatParam = (SugarcoatParameter) o;
		return getId() != null && Objects.equals(getId(), sugarcoatParam.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public void resetValue() {
		value = defaultValue;
	}

}