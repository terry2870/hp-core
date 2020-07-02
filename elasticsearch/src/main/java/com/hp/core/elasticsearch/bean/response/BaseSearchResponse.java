package com.hp.core.elasticsearch.bean.response;

import com.hp.core.common.beans.BaseBean;

/**
 * 
 * @author huangping
 * Jun 22, 2020
 */
public class BaseSearchResponse extends BaseBean{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1769608756868027913L;
	private Object[] sortValues;

    public Object[] getSortValues() {
        return sortValues;
    }

    public void setSortValues(Object[] sortValues) {
        this.sortValues = sortValues;
    }
}
