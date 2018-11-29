package com.github.liuweijw.provider.mybatis.page;

import java.util.ArrayList;
import java.util.List;

import com.github.liuweijw.commons.base.page.PageBean;

/**
 * 分页工具 注意分页是从0开始的
 * 
 * @author liuweijw
 */
public class MPageUtil {

	public static <T> PageBean<T> of(List<T> resultList, Integer total, Integer currentPage,
			Integer pageSize) {
		total = (null == total || total < 0 ? 0 : total);
		PageBean<T> pageData = new PageBean<T>();
		pageData.setCurrentPage(currentPage);
		pageData.setPageSize(pageSize);
		pageData.setTotal(Long.valueOf(total));
		List<T> newList = new ArrayList<T>();
		if (null != resultList && resultList.size() > 0) newList.addAll(resultList);
		pageData.setList(newList);
		return pageData;
	}

	public static <T> PageBean<T> of(List<T> resultList, PageInfo pageInfo) {
		return of(resultList, pageInfo.getTotal(), pageInfo.getCurrentPage(), pageInfo.getPageSize());
	}

}
