package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.dict.Dictionary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author xxd
 * @version 1.0
 * @description: 基础字典信息
 * @date 2023/3/20
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
    private String dictionaryId;

    @Column(length = 32)
    private String code;

    @Column(length = 32)
    private String name;

    @Column(length = 32)
    private String groupCode;

    @Column(length = 32, name = "group_id", insertable = false, updatable = false)
    private String groupId;

    @ToString.Exclude
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private SugarcoatDictionaryGroup dictionaryGroup;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
