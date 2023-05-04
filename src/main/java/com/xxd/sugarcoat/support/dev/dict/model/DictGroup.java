package com.xxd.sugarcoat.support.dev.dict.model;

import com.xxd.sugarcoat.support.dev.orm.BaseEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/19 23:04
 */
@Entity
@Getter
@Setter
@ToString
public class DictGroup extends BaseEntity {

    @Column(length = 32)
    private String groupCode;
    @Column(length = 32)
    private String groupName;
    @OneToMany(mappedBy = "dictGroup", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DictItem> dictItems = new java.util.ArrayList<>();

}
