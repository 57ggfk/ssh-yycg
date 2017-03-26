package cn.itcast.yycg.cgd.service;

import java.util.List;

import cn.itcast.yycg.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.cgd.pojo.YycgdQueryCustom;
import cn.itcast.yycg.domain.po.Yycgd;

public interface YycgdService {

	/**
	 * 保存医药采购单
	 * @param yycgdCustom
	 * @return 
	 */
	public Integer saveYycgd(YycgdCustom yycgdCustom) ;
	
	/**
	 * 条件查询采购单总数 
	 */
	public int getTotalRecord(YycgdQueryCustom yycgdQueryCustom);
	
	/**
	 * 条件查询采购单+分页
	 */
	public List<Yycgd> findAll(YycgdQueryCustom yycgdQueryCustom, int offset, int limit);

	/**
	 * 根据id查询医药采购单
	 * @param id
	 * @return
	 */
	public Yycgd findById(Integer id);

	/**
	 * 卫生室修改采购单或提交采购单
	 * @param yycgdCustom
	 */
	public void updateYyycg(YycgdCustom yycgdCustom);
	
	/**
	 * 供应商受理:完成受理
	 * @param cgdId
	 * @param gysId
	 * @return
	 */
	public void updatedispose(Integer cgdId, String gysId);

	/**
	 * 删除未提交的采购单
	 * @param yycgdCustom
	 */
	public void deleteYycgd(YycgdCustom yycgdCustom);

	/**
	 * 采购单审核并提交
	 * @param yycgdCustom
	 */
	public void updateYyycg_check(YycgdCustom yycgdCustom);
}
