package com.sugarcoat.uims.domain;

import com.sugarcoat.protocol.common.BooleanEnum;
import com.sugarcoat.protocol.common.EnumConvert;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * TODO
 *
 * @author 许向东
 * @date 2023/10/26
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DemoDo {

    @Id
    private String id;

    private String name;

    @Convert(converter = BooleanEnum.Convert.class)
    private BooleanEnum status;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DemoDo demoDo = (DemoDo) o;
        return getId() != null && Objects.equals(getId(), demoDo.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
