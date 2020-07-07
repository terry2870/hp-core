/**
 * 
 */
package com.hp.core.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hp.Application;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.database.enums.DirectionEnum;
import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.dal.model.BillInfo;
import com.hp.core.test.service.TestService;

/**
 * @author huangping
 * Jul 16, 2019
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Test {

	@Autowired
	private IBillInfoDAO billInfoDAO;
	
	@org.junit.Test
	public void aa() {
		System.out.println("asdasdasdasd");
		//testService.count();
		//System.out.println(billInfoDAO.selectBy(2));
		
		
		//billInfoDAO.selectByPrimaryKey(2);
//		List<BillInfo> billList = billInfoDAO.selectList(new SQLBuilders()
//				.select("id", "project_id")
//				.where(SQLWhere.builder()
//						.eq("id", 2)
//						.build())
//				.order(OrderBy.of("id", "desc"), OrderBy.of("create_time", DirectionEnum.ASC))
//				.page(2, 20)
//				);
		
		
		Integer a = billInfoDAO.selectMaxId();
		System.out.println("a= " + a);
		
//		System.out.println("billList= " + billList);
	}
	
}
