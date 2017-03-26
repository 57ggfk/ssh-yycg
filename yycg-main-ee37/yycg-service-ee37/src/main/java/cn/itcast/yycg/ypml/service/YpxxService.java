package cn.itcast.yycg.ypml.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.province.service.YpxxSheng;
import cn.itcast.yycg.ypml.pojo.YpxxQueryCustom;

public interface YpxxService {

	/**
	 * 查询所有药品信息
	 * @param ypxxQueryCustom
	 * @return
	 */
	public List<Ypxx> findAll(YpxxQueryCustom ypxxQueryCustom);

	/**
	 * 保存同步的药品信息们
	 * @param allYpxxShend
	 * @return
	 */
	public int saveSyncypxx(List<YpxxSheng> allYpxxSheng);

	/**
	 * 条件查询药品总数
	 * @param ypxxQueryCustom
	 * @return
	 */
	public int getTotalRecord(YpxxQueryCustom ypxxQueryCustom);
	
	/**
	 * 查询药品信息，条件+分页
	 */
	public List<Ypxx> findAll(YpxxQueryCustom ypxxQueryCustom,int offset,int limit);
}
