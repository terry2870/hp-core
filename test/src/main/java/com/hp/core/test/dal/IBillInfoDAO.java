package com.hp.core.test.dal;

import java.util.List;

import com.hp.core.database.dao.BaseDAO;
import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.test.dal.model.BillInfo;

public interface IBillInfoDAO extends BaseDAO<BillInfo> {
	List<BillInfo> selectBy(Integer id);
}