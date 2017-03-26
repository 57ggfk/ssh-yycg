package cn.itcast.yycg.cgd.dao;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.domain.po.YycgdMx;

public interface YycgdMxDao extends BaseDao<YycgdMx> {

	/**
	 * 查询明细，根据采购单和药品信息
	 * @param yycgd
	 * @param ypxx
	 * @return
	 */
	YycgdMx findByUnique(Yycgd yycgd, Ypxx ypxx);

}
