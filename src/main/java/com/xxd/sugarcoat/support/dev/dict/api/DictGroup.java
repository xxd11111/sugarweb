package com.xxd.sugarcoat.support.dev.dict.api;

import com.xxd.sugarcoat.support.dev.orm.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/19 23:04
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DictGroup extends BaseEntity {

    @Column(length = 32)
    private String groupCode;
    @Column(length = 32)
    private String groupName;
    @OneToMany
    @ToString.Exclude
    private List<Dict> dictList;

}
