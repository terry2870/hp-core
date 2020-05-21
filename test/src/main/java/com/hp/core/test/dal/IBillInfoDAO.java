package com.hp.core.test.dal;

import java.util.List;

import com.hp.core.database.dao.BaseDAO;
import com.hp.core.test.dal.model.BillInfo;

public interface IBillInfoDAO extends BaseDAO<BillInfo, Integer> {
	List<BillInfo> selectBy(Integer id);
}