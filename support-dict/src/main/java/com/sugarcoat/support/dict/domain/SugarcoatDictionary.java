package com.sugarcoat.support.dict.domain;

import com.sugarcoat.protocol.dictionary.Dictionary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;

    @Column(length = 32)
    private String group;

    @Column(length = 32)
    private String code;

    @Column(length = 32)
    private String name;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
