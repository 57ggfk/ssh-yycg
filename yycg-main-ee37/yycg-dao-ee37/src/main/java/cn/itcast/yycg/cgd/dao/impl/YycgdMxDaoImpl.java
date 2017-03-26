package cn.itcast.yycg.cgd.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.cgd.dao.YycgdMxDao;
import cn.itcast.yycg.cgd.dao.query.YycgdMxQuery;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.domain.po.YycgdMx;

@Repository
@SuppressWarnings("unchecked")
public class YycgdMxDaoImpl extends BaseDaoImpl<YycgdMx> implements YycgdMxDao {

	/**
	 * 封装条件的方法
	 */
	@Override
	protected DetachedCriteria getDetachedCriteria(YycgdMx t) {
		YycgdMxQuery yycgdMxQuery = (YycgdMxQuery) t;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(YycgdMx.class);
		if (yycgdMxQuery != null) {
			
			// 采购单对应的采购单详情
			if (yycgdMxQuery.getYycgd() != null) {
				detachedCriteria.add(Restrictions.eq("yycgd", yycgdMxQuery.getYycgd()));
			}
			// 和药品信息有关的条件
			if (yycgdMxQuery.getYpxx() != null) {
				//创建药品相关条件
				DetachedCriteria dcYpxx = detachedCriteria.createCriteria("ypxx");
				
				// 交易状态
				SysDictInfo jyzt = yycgdMxQuery.getYpxx().getSysDictInfoByJyzt();
				if (jyzt != null && StringUtils.isNotBlank(jyzt.getId())) {
					dcYpxx.add(Restrictions.eq("sysDictInfoByJyzt", jyzt));
				}
				
				// 药品类别
				SysDictInfo lb = yycgdMxQuery.getYpxx().getSysDictInfoByLb();
				if (lb != null && StringUtils.isNotBlank(lb.getId())) {
					dcYpxx.add(Restrictions.eq("sysDictInfoByLb", lb));
				}
				
				// 价格范围
				Float zbjglower = yycgdMxQuery.getZbjglower();
				if (zbjglower != null && zbjglower != 0) {
					dcYpxx.add(Restrictions.gt("zbjg", yycgdMxQuery.getZbjglower()));
				}
				Float zbjgupper = yycgdMxQuery.getZbjgupper();
				if ( zbjgupper!= null && zbjgupper != 0) {
					dcYpxx.add(Restrictions.lt("zbjg", yycgdMxQuery.getZbjgupper()));
				}
				
				//转换系数精确匹配
				String zhxs = yycgdMxQuery.getYpxx().getZhxs();
				if(StringUtils.isNotBlank(zhxs)) {
					dcYpxx.add(Restrictions.eqOrIsNull("zhxs", zhxs));
				}
				
				//普通文本字段统一处理为模糊匹配
				dcYpxx.add(getExample(t.getYpxx()));
			}
			
			// 采购状态
			SysDictInfo cgzt = yycgdMxQuery.getSysDictInfoCgzt();
			if (cgzt != null && StringUtils.isNotBlank(cgzt.getId())) {
				detachedCriteria.add(Restrictions.eq("sysDictInfoCgzt.id", cgzt.getId()));
			}
		}
		return detachedCriteria;
	}

	@Override
	public YycgdMx findByUnique(Yycgd yycgd, Ypxx ypxx) {
		DetachedCriteria dc = DetachedCriteria.forClass(YycgdMx.class);
		dc.add(Restrictions.eq("yycgd", yycgd));
		dc.add(Restrictions.eq("ypxx", ypxx));
		List<YycgdMx> list = (List<YycgdMx>) this.getHibernateTemplate().findByCriteria(dc);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
