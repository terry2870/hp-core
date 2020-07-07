/**
 * 
 */
package com.hp.core.test.daltest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.core.common.junit.BaseJUnitTest;
import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.dal.ITestTableDAO;

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
	
	@org.junit.Test
	public void test() {
		Integer count = testTableDAO.selectCount(null);
		System.out.println("count= " + count);
	}
	
	@org.junit.Test
	public void test2() {
	}
	
}
