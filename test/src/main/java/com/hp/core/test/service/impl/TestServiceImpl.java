/**
 * 
 */
package com.hp.core.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.core.common.exceptions.CommonException;
import com.hp.core.test.dal.IBillInfoDAO;
import com.hp.core.test.dal.model.BillInfo;
import com.hp.core.test.service.TestService;

/**
 * @author huangping
 * Jan 17, 2020
 */
//@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private IBillInfoDAO billInfoDAO;
	
	@Override
	@Transactional
	public Integer count() {
		System.out.println("billInfoDAO= " + billInfoDAO.selectByPrimaryKey(2));
		
		BillInfo bill = new BillInfo();
		bill.setId(2);
		bill.setExpressContacts("哈哈");
		bill.setExpressContactsMobile("133ddd");
		billInfoDAO.updateByPrimaryKeySelective(bill);
		System.out.println("billInfoDAO2= " + billInfoDAO.selectByPrimaryKey(2));
		if (true) {
			throw new CommonException(500, "异常了");
		}
		return null;
	}
}
