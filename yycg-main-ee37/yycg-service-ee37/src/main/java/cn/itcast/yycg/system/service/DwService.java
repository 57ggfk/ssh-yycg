package cn.itcast.yycg.system.service;

import java.util.List;

import cn.itcast.yycg.domain.po.DwWsy;

public interface DwService {
	
	/**
	 * 根据用户类别的dictcode查询单位
	 * @param dictcode
	 * @return
	 */
	public List<?> findDwByDictcode(String dictcode);
	/**
	 * 获取用户信息
	 * @param sysid
	 * @return
	 */
	public void findAllByWsy(String sysid);
	
}
