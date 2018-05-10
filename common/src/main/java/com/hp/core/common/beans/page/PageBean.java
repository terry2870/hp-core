package com.hp.core.common.beans.page;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.common.utils.StringUtil;

/**
 * 分页对象
 * @author ping.huang
 *
 */
public class PageBean extends BaseBean {

	static Logger log = LoggerFactory.getLogger(PageBean.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7111590533083251807L;
	private int pageNo = 1;// 页码，默认是第一页
	private int pageSize = 10;// 每页显示的记录数，默认是10
	private int totalRecord;// 总记录数
	private int totalPage;// 总页数
	private int startIndex = 0;//查询的开始行数
	
	/**
	 * 排序字段
	 */
	private String sortName;
	
	/**
	 * 排序方式
	 */
	private String order = "ASC";

	private Map<String, Object> params = new HashMap<String, Object>();// 其他的参数我们把它分装成一个Map对象
	
	/**
	 * 初始化分页信息
	 * @param page
	 * @param o
	 * @return
	 */
	public static PageBean initPageInfo(PageRequest page, Object o) {
		PageBean pageBean = new PageBean();
		if (page.getPage() > 0) {
			pageBean.setPageNo(page.getPage());
		}
		if (page.getRows() > 0) {
			pageBean.setPageSize(page.getRows());
		}
		pageBean.setSortName(StringUtil.toDBColumn(page.getSort()));
		pageBean.setOrder(page.getOrder());
		pageBean.setStartIndex((pageBean.getPageNo() - 1) * pageBean.getPageSize());
		if (o != null) {
			try {
				Field[] declaredFields = o.getClass().getDeclaredFields();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					// 过滤内容为空的
					if (field.get(o) == null) {
						continue;
					}
					pageBean.getParams().put(field.getName(), field.get(o));
				}

			} catch (IllegalAccessException e) {
				log.error("", e);
			}
		}
		return pageBean;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		// 在设置总页数的时候计算出对应的总页数
		int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
		this.setTotalPage(totalPage);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}
