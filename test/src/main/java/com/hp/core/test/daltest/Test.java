/**
 * 
 */
package com.hp.core.test.daltest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.core.common.junit.BaseJUnitTest;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.dal.ITestTableDAO;
import com.hp.core.test.dal.ITestTableDAO2;
import com.hp.core.test.dal.model.TestTable;

/**
 * @author huangping
 * 2018年3月31日 下午11:59:59
 */
public class Test extends BaseJUnitTest {
	
	static Logger log = LoggerFactory.getLogger(Test.class);

	@Autowired
	private IBillInfoDAO billInfoDAO;
	@Autowired
	private ITestTableDAO testTableDAO;
	@Autowired
	private ITestTableDAO2 testTableDAO2;
	
	@org.junit.Test
	public void test() {
		Integer count = testTableDAO.selectCount();
		System.out.println("count= " + count);
	}
	
	@org.junit.Test
	public void test2() {
	}
	
}
