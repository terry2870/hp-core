/**
 * 
 */
package com.hp.core.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.service.TestService;

/**
 * @author huangping
 * Jul 16, 2019
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class Test {

	@Autowired
	private IBillInfoDAO billInfoDAO;
	@Autowired
	private TestService testService;
	
	@org.junit.Test
	public void aa() {
		System.out.println("asdasdasdasd");
		testService.count();
		//System.out.println(billInfoDAO.selectBy(2));
		
		
		//billInfoDAO.selectByPrimaryKey(2);
	}
	
}
