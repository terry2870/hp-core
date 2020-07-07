/**
 * 
 */
package com.hp;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.core.common.beans.Response;
import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.test.dal.ITestTableDAO;
import com.hp.core.test.dal.model.BillInfo;
import com.hp.core.test.dal.model.ProjectInfo;
import com.hp.core.test.dal.model.TestTable;

/**
 * @author ping.huang
 * 2017年8月28日
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportResource(locations = {"classpath*:META-INF/spring/spring-*.xml"})
@MapperScan(basePackages = {"com.hp.core.test.dal"})
public class Application {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		log.info("start Application");
		try {
			SpringApplication.run(Application.class, args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("start success Application");
	}

	@RestController
	@RequestMapping("/test")
	public static class Rest {
		
		@Autowired
		private BaseMapper<TestTable, Integer> dao1;
		@Autowired
		private BaseMapper<BillInfo, Integer> dao2;
		
		@RequestMapping("/test")
		public Response<Object> test() {
			System.out.println(dao1.selectCount(null));
			System.out.println(dao2.selectCount(null));
			return Response.success();
		}
	}
}
