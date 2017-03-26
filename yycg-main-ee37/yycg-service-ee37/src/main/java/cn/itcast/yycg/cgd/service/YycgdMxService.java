package cn.itcast.yycg.cgd.service;

import java.util.List;

import cn.itcast.yycg.cgd.pojo.YycgdMxQueryCustom;
import cn.itcast.yycg.domain.po.YycgdMx;

public interface YycgdMxService {

	/**
	 * 查询采购单明细总数
	 * @param yycgdQueryCustom
	 */
	public int getTotalRecord(YycgdMxQueryCustom yycgdMxQueryCustom);
	
	/**
	 * 查询采购明细，条件+分页
	 */
	public List<YycgdMx> findAll(YycgdMxQueryCustom yycgdMxQueryCustom,int offset, int limit);

	/**
	 * 保存采购单明细药品列表
	 * @param id
	 * @param ypxxids
	 */
	public void saveYycgdMxList(Integer id, String[] ypxxids);

	/**
	 * 保存采购单明细药品采购量，批量
	 * @param yycgdmxIds
	 * @param yycgdmxCgls
	 */
	public void saveYycgdMxCgls(String[] yycgdmxIds, Integer[] yycgdmxCgls);

	/**
	 * 删除采购单明细药品项，批量
	 * @param yycgdmxIds
	 */
	public void deleteYycgdMxIds(String[] yycgdmxIds);
	
	/**
	 * 保存药品发货状态
	 * @param yycgdmxIds
	 * @param yycgdmxztIds
	 */
	public void saveYycgdMxZt(Integer cgdId,String[] yycgdmxIds, String[] yycgdmxztIds);
}
