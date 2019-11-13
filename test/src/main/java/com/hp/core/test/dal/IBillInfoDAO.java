package com.hp.core.test.dal;

import java.util.List;

import com.hp.core.mybatis.mapper.BaseSelectMapper;
import com.hp.core.test.dal.model.BillInfo;

public interface IBillInfoDAO extends BaseSelectMapper<BillInfo> {
	List<BillInfo> selectBy(Integer id);
}