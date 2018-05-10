package com.hp.core.test.dal;

import java.util.List;

import com.hp.core.test.dal.model.BillInfo;

public interface IBillInfoDAO {
	List<BillInfo> selectBy(Integer id);
}