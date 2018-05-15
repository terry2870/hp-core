/**
 * 
 */
package com.hp.core.test.dal;
/**
 * @author huangping
 * 2018年5月14日
 */

import com.gitee.hengboy.mybatis.enhance.mapper.EnhanceMapper;
import com.hp.core.test.dal.model.TestTable;

public interface ITestTableDAO extends EnhanceMapper<TestTable, Integer> {

	TestTable selectById(Integer id);
	
	int updateById(TestTable test);
}
