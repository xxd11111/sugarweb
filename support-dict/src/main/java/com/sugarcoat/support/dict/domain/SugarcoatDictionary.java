package com.sugarcoat.support.dict.domain;

import com.sugarcoat.api.common.BooleanFlag;
import com.sugarcoat.api.dict.Dictionary;
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
    private String dictionaryId;

    /**
     * 字典编码
     */
    @Column(length = 32)
    private String code;

    /**
     * 字典名称
     */
    @Column(length = 32)
    private String name;

    /**
     * 是否系统内置
     */
    @Column(length = 32)
    private BooleanFlag innerFlag;

    /**
     * 组id
     */
    @Column(length = 32, name = "group_id", insertable = false, updatable = false)
    private String groupId;

    @Column(length = 32)
    private String groupCode;

    @ToString.Exclude
    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private SugarcoatDictionaryGroup dictionaryGroup;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
