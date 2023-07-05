package com.sugarcoat.api.common;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @description 分页数据
 * @date 2022-11-13
 */
@Getter
@Setter
public class PageData<T> {

	/**
	 * 分页数据
	 */
	private List<T> records;

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 每页记录数
	 */
	private long size;

	/**
	 * 当前页数
	 */
	private long page;

	public PageData() {
	}

	public PageData(List<T> records, long total, long size, long page) {
		this.records = records;
		this.total = total;
		this.size = size;
		this.page = page;
	}

	public static <T> PageData<T> empty() {
		return new PageData<>(new ArrayList<>(), 0, 0, 0);
	}

}
