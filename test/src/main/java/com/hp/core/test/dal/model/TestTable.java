/**
 * 
 */
package com.hp.core.test.dal.model;


import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年5月14日
 */
@Table(name = "test_table")
public class TestTable extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -463188118297996495L;

	@Id(generatorType = KeyGeneratorTypeEnum.AUTO)
	private Integer id;
	private String title;
	private String simplified;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSimplified() {
		return simplified;
	}
	public void setSimplified(String simplified) {
		this.simplified = simplified;
	}
}
