/**
 * 
 */
package com.hp.core.test.daltest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hp.core.common.junit.BaseJUnitTest;
import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.dal.ITablesDAO;
import com.hp.core.test.dal.ITestTableDAO;
import com.hp.core.test.dal.model.BillInfo;
import com.hp.core.test.dal.model.TestTable;

/**
 * @author huangping
 * 2018年3月31日 下午11:59:59
 */
public class Test extends BaseJUnitTest {

	@Autowired
	private IBillInfoDAO billInfoDAO;
	@Autowired
	private ITablesDAO tablesDAO;
	@Autowired
	private ITestTableDAO testTableDAO;
	
	@org.junit.Test
	public void test() {
		
//		System.out.println(1);
//		List<BillInfo> num = billInfoDAO.selectBy(2);
//		System.out.println("num= " + JSON.toJSONString(num));
//		
//		int n = tablesDAO.selectBy();
//		System.out.println("tablesDAO= " + n);
		
		for (int i = 0; i < 10; i++) {
			TestTable test = testTableDAO.selectById(1);
			System.out.println("test=    " + test);
		}
		
		
		
//		TestTable test = new TestTable();
//		test.setId(1);
//		test.setSimplified("哈哈12345");
//		testTableDAO.updateById(test);
	}
}
