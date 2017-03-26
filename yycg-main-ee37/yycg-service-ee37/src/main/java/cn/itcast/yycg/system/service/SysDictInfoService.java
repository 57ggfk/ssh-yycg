package cn.itcast.yycg.system.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysDictInfo;

public interface SysDictInfoService {

	/**
	 * 根据字典类型码typecode查询所有字段数据
	 * @param typecode
	 * @return
	 */
	List<SysDictInfo> findAll(String typecode);
	
	SysDictInfo findById(String id);

}
