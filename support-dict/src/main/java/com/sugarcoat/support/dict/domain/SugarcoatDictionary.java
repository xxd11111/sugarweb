package com.sugarcoat.support.dict.domain;

import com.sugarcoat.protocol.dictionary.Dictionary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

/**
 * 字典信息
 *
 * @author xxd
 * @version 1.0
 * @since 2023/3/20
 */
@Entity
@Getter
@Setter
@ToString
public class SugarcoatDictionary implements Dictionary {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(length = 40)
    private String id;

    @Column(length = 32)
    private String dictGroup;

    @Column(length = 32)
    private String dictCode;

    @Column(length = 32)
    private String dictName;

    @Column(length = 1)
    private String dictType;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
