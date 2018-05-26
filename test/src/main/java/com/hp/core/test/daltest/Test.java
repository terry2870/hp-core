/**
 * 
 */
package com.hp.core.test.daltest;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hp.core.common.junit.BaseJUnitTest;
import com.hp.core.mybatis.annotation.ForceMaster;
import com.hp.core.test.bean.UserBean;
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
	
	static Logger log = LoggerFactory.getLogger(Test.class);

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
			//TestTable test = testTableDAO.selectById(1);
			//System.out.println("test=    " + test);
		}
		TestTable t = testTableDAO.selectByPrimaryKey(1);
		System.out.println("t= " + t);
		
		System.out.println(testTableDAO.selectAllCount());
		
		System.out.println(billInfoDAO.selectAllCount());
		System.out.println(billInfoDAO.selectByPrimaryKey(2));
		
	}
	
	
	public static void main(String[] args) {
		Class<?>[] clazz = {UserBean.class, UserBean.class, BillInfo.class};
		ExecutorService exe = Executors.newFixedThreadPool(3);
		for (Class<?> c : clazz) {
			exe.execute(new Run(c));
		}
	}
	
	static class Run implements Runnable {

		public Run(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		private Class<?> clazz;
		
		@Override
		public void run() {
			tt(clazz);
		}
		
		private void tt(Class<?> clazz) {
			synchronized (clazz) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				log.info("-----" + clazz.getName());
			}
			
			
		}
		
	}
	
}
