package com.hp.core.test.dal;

import java.util.List;

import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.test.dal.model.BillInfo;

public interface IBillInfoDAO extends BaseMapper<BillInfo> {
	List<BillInfo> selectBy(Integer id);
}