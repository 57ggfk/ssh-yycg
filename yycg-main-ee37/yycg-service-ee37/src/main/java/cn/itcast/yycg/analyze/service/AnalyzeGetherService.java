package cn.itcast.yycg.analyze.service;

import cn.itcast.yycg.domain.po.SysTask;

public interface AnalyzeGetherService {

	/**
	 * @param sysTask
	 * 采购单已审核
	 */
	public void saveTask01(SysTask sysTask);

	/**
	 * @param sysTask
	 * 采购单已发货
	 */

	public void saveTask02(SysTask sysTask);

	/**
	 *  @param sysTask
	 * 	采购单已入库
	 */
	public void saveTask03(SysTask sysTask);

	
	
}
