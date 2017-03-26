package cn.itcast.yycg.system.dao;


import java.util.List;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.SysDictInfo;

public interface SysDictInfoDao extends BaseDao<SysDictInfo>{

	/**
	 * @param dictCode
	 * @return
	 */
	List<SysDictInfo> findByDictCode(String dictCode);

	/**
	 * 通过TypeCode查询对应的信息
	 * @param string
	 * @return
	 */
	List<SysDictInfo> findByTypeCode(String string);
	
}
