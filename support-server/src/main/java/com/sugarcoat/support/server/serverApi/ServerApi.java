package com.sugarcoat.support.server.serverApi;

import com.sugarcoat.orm.EntityExt;
import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * 服务接口
 *
 * @author xxd
 * @date 2022-10-25
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ServerApi extends EntityExt {

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
		ServerApi serverApi = (ServerApi) o;
		return getId() != null && Objects.equals(getId(), serverApi.getId());
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}
