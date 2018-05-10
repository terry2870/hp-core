/**
 * 
 */
package com.hp.core.test.daltest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.dal.ITablesDAO;
import com.hp.core.test.dal.model.BillInfo;
import com.hp.tools.common.junit.BaseJUnitTest;

/**
 * @author huangping
 * 2018年3月31日 下午11:59:59
 */
public class Test extends BaseJUnitTest {

	@Autowired
	private IBillInfoDAO billInfoDAO;
	@Autowired
	private ITablesDAO tablesDAO;
	
	@org.junit.Test
	public void test() {
		System.out.println(1);
		List<BillInfo> num = billInfoDAO.selectBy(2);
		System.out.println("num= " + JSON.toJSONString(num));
		
		int n = tablesDAO.selectBy();
		System.out.println("tablesDAO= " + n);
		
	}
}
