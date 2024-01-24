package com.sugarcoat.uims.domain.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * 安全日志
 *
 * @author xxd
 * @version 1.0
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class SecurityLog {

    /**
     * 事件类型
     */
    @Id
    private String id;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作ip
     */
    private String actionIp;

    /**
     * 操作类型
     */
    private String actionType;

    /**
     * 相关信息
     */
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SecurityLog that = (SecurityLog) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
