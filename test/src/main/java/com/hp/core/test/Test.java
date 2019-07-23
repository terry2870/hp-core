/**
 * 
 */
package com.hp.core.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.core.test.dal.IBillInfoDAO;

/**
 * @author huangping
 * Jul 16, 2019
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:META-INF/spring/spring*.xml"})
public class Test {

	@Autowired
	private IBillInfoDAO billInfoDAO;
	
	@org.junit.Test
	public void aa() {
		System.out.println("asdasdasdasd");
		System.out.println("billInfoDAO= " + billInfoDAO.selectAllCount());
		System.out.println(billInfoDAO.selectBy(2));
	}
}
