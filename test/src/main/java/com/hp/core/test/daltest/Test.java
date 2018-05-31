/**
 * 
 */
package com.hp.core.test.daltest;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hp.core.common.beans.page.PageBean;
import com.hp.core.common.beans.page.PageModel;
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
	private ITestTableDAO testTableDAO;
	
	@org.junit.Test
	public void test() {
		
//		System.out.println(1);
//		List<BillInfo> num = billInfoDAO.selectBy(2);
//		System.out.println("num= " + JSON.toJSONString(num));
//		
//		int n = tablesDAO.selectBy();
//		System.out.println("tablesDAO= " + n);
		
		BillInfo bill = new BillInfo();
//		bill.setId(2);
//		bill.setEnterpriseName("爱仕达多asdasd");
//		bill.setExpressCode("asdhakjsdhkajshd");
//		bill.setExpressContacts("我们的1234");
		bill.setProjectId(2);
		//System.out.println("update ======= " + billInfoDAO.updateByPrimaryKeySelective(bill));
		//bill.setCreateTime(1487230903);
		
		//System.out.println(billInfoDAO.selectAllCount());
//		System.out.println("-----" + billInfoDAO.selectByPrimaryKey(2));
		//System.out.println(billInfoDAO.selectCountByParams(bill));
		PageModel page = new PageModel();
		page.setCurrentPage(1);
		page.setPageSize(2);
		page.setSortColumn("projectId");
		
		Integer total = billInfoDAO.selectCountByParams(bill);
		System.out.println("total= " + total);
		
		List<BillInfo> list = billInfoDAO.selectListByParams(bill);
		System.out.println("list1 = " + JSON.toJSONString(list));
		
		list = billInfoDAO.selectListByParamsWithPage(bill, page);
		System.out.println("list2 = " + JSON.toJSONString(list));
		
		list = billInfoDAO.selectByPrimaryKeys(2);
		System.out.println("list3 = " + JSON.toJSONString(list));
		
//		TestTable test = new TestTable();
//		test.setSimplified("simplified___" + 1);
//		//test.setTitle("title_" + 1);
//		//test.setTestName("testName_" + 1);
//		System.out.println("insert   " + testTableDAO.insertSelective(test));
//		System.out.println("id===" + test.getId());
		
//		TestTable test = null;
//		List<TestTable> list = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			test = new TestTable();
//			test.setSimplified("sim___" + i);
//			test.setTestName("name___" + i);
//			test.setTitle("tit___" + i);
//			list.add(test);
//		}
//		testTableDAO.insertBatch(list);
		
//		TestTable test = testTableDAO.selectByPrimaryKey(1);
//		System.out.println("test= " + test);
//		
//		
//		testTableDAO.deleteByPrimaryKeys(1,2,3);
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
