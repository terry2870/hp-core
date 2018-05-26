/**
 * 
 */
package com.hp.core.test.dal;
/**
 * @author huangping
 * 2018年5月14日
 */

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hp.core.mybatis.annotation.ForceMaster;
import com.hp.core.mybatis.annotation.ForceSlave;
import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.test.dal.model.TestTable;
import com.hp.core.test.provider.SelectPro;

public interface ITestTableDAO extends BaseMapper<TestTable, Integer> {

	@ForceMaster
	TestTable selectById(Integer id);
	
	int updateById(TestTable test);
	
	@ForceSlave
	@SelectProvider(type = SelectPro.class, method = "getSelectSQL")
	TestTable s1electFromProvide(@Param("id") int id, String str);
}
