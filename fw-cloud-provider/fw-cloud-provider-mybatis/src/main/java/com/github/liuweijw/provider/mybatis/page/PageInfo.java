package com.github.liuweijw.provider.mybatis.page;

import com.github.liuweijw.commons.base.page.PageParams;

/**
 * 类名称：PageInfo
 * 类描述：PageInfo 分页实体类
 */
public class PageInfo extends PageParams {

	/** 总记录数 */
	private Integer	total	= 0;

	/** 排序字段 */
	private String	sort;

	/** ASC,DESC mybatis Order 关键字 */
	private String	order;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
