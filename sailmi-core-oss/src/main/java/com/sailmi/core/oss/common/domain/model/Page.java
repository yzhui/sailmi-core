package com.sailmi.core.oss.common.domain.model;

import java.util.List;

/**
 * 分页
 * @param <T>
 */
public class Page<T> {

	// 结果集
	private List<T> rows;

	// 查询记录数
	private int total;

	// 每页多少条数据
	private int pageSize = 10;

	// 第几页
	private int pageNo = 1;

	// 跳过几条数
	private int skip = 0;

	/**
	 * 总页数
	 * @return
	 */
	public int getTotalPages() {

		return (total + pageSize - 1) / pageSize;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
	}

	public int getSkip() {

		skip = (pageNo - 1) * pageSize;
		return skip;
	}

	public void setSkip(int skip) {

		this.skip = skip;
	}

	public int getPageNo() {

		return pageNo;
	}

	public void setPageNo(int pageNo) {
		// offset
		if (pageNo == 0) {
			this.pageNo = pageNo+1;
		} else {
			this.pageNo = pageNo;
		}

	}
}
