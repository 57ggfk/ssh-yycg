package cn.itcast.yycg.ypml.dao;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.ypml.dao.query.YpxxQuery;

public interface YpxxDao extends BaseDao<Ypxx> {

	Ypxx findByBm(String bm);

	Ypxx findByUnique(YpxxQuery ypxxQuery);

}
