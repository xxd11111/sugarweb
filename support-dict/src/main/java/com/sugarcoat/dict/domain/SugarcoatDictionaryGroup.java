package com.sugarcoat.dict.domain;

import com.sugarcoat.api.dict.DictionaryGroup;
import com.sugarcoat.orm.EntityExt;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;

/**
 * @author xxd
 * @description TODO
 * @date 2023/4/19 23:04
 */
@Entity
@Getter
@Setter
@ToString
public class SugarcoatDictionaryGroup extends EntityExt implements DictionaryGroup {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String groupId;

	@Column(length = 32)
	private String groupCode;

	@Column(length = 32)
	private String groupName;

	@OneToMany(mappedBy = "dictionaryId", cascade = CascadeType.ALL)
	@ToString.Exclude
	private Collection<SugarcoatDictionary> dictionaries = new java.util.ArrayList<>();

}
