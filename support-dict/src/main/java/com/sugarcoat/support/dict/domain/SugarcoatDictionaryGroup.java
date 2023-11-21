package com.sugarcoat.support.dict.domain;

import com.sugarcoat.support.orm.BooleanEnum;
import com.sugarcoat.protocol.dict.Dictionary;
import com.sugarcoat.protocol.dict.DictionaryGroup;
import com.sugarcoat.support.orm.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 字典组
 *
 * @author xxd
 * @since 2023/4/19 23:04
 */
@Entity
@Getter
@Setter
@ToString
public class SugarcoatDictionaryGroup extends AuditEntity implements DictionaryGroup {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String groupId;

	@Column(length = 32)
	private String groupCode;

	@Column(length = 32)
	private String groupName;

	/**
	 * 是否系统内置
	 */
	@Column(length = 1)
	@Enumerated(EnumType.ORDINAL)
	private BooleanEnum innerFlag;

	@OneToMany(mappedBy = "dictionaryId", cascade = CascadeType.ALL)
	@ToString.Exclude
	@Transient
	private List<SugarcoatDictionary> sugarcoatDictionaries = new java.util.ArrayList<>();

	/**
	 * 将sugarcoatDictionaries与getDictionaries分开处理泛型转型问题
	 */
	@Override
	public Collection<Dictionary> getDictionaries() {
		return new ArrayList<>(sugarcoatDictionaries);
	}
}
