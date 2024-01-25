package com.sugarweb.server.domain;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * 服务接口
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ApiInfo implements Cloneable {

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
        ApiInfo apiInfo = (ApiInfo) o;
        return getOperationId() != null && Objects.equals(getOperationId(), apiInfo.getOperationId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public ApiInfo clone() {
        try {
            return (ApiInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
