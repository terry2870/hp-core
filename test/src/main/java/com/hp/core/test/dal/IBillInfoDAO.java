package com.hp.core.test.dal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.test.dal.model.BillInfo;

public interface IBillInfoDAO extends BaseMapper<BillInfo, Integer> {
	List<BillInfo> selectBy(Integer id);
	
	List<BillInfo> select(@Param("a") Map<String, Object> map);
}