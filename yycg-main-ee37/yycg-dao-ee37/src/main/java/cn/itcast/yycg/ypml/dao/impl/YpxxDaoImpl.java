package cn.itcast.yycg.ypml.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.ypml.dao.YpxxDao;
import cn.itcast.yycg.ypml.dao.query.YpxxQuery;

@Repository
@SuppressWarnings("unchecked")
public class YpxxDaoImpl extends BaseDaoImpl<Ypxx> implements YpxxDao {

	@Override
	protected DetachedCriteria getDetachedCriteria(Ypxx t) {
		DetachedCriteria dc = super.getDetachedCriteria(t);
		//强转为查询对象
		YpxxQuery query = (YpxxQuery) t;
			
			// 和药品信息有关的条件
			if (query != null) {
				//创建药品相关条件
				
				// 交易状态
				SysDictInfo jyzt = query.getSysDictInfoByJyzt();
				if (jyzt != null && StringUtils.isNotBlank(jyzt.getId())) {
					dc.add(Restrictions.eq("sysDictInfoByJyzt", jyzt));
				}
				
				// 药品类别
				SysDictInfo lb = query.getSysDictInfoByLb();
				if (lb != null && StringUtils.isNotBlank(lb.getId())) {
					dc.add(Restrictions.eq("sysDictInfoByLb", lb));
				}
				
				// 价格范围
				Float zbjglower = query.getZbjglower();
				if (zbjglower != null && zbjglower != 0) {
					dc.add(Restrictions.gt("zbjg", query.getZbjglower()));
				}
				Float zbjgupper = query.getZbjgupper();
				if ( zbjgupper!= null && zbjgupper != 0) {
					dc.add(Restrictions.lt("zbjg", query.getZbjgupper()));
				}
				
				//转换系数精确匹配
				String zhxs = query.getZhxs();
				if(StringUtils.isNotBlank(zhxs)) {
					dc.add(Restrictions.eqOrIsNull("zhxs", zhxs));
				}
				//zhxs使用后设置为null，放置Example重新生成条件
				query.setZhxs(null);
				
				//普通文本字段统一处理为模糊匹配
				dc.add(getExample(query));
			}

		return dc;
	}

	@Override
	public Ypxx findByBm(String bm) {
		DetachedCriteria dc = DetachedCriteria.forClass(Ypxx.class);
		dc.add(Property.forName("bm").eq(bm));
		List<Ypxx> list = (List<Ypxx>) this.getHibernateTemplate().findByCriteria(dc);
		if (list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Ypxx findByUnique(YpxxQuery ypxxQuery) {
		DetachedCriteria dc = DetachedCriteria.forClass(Ypxx.class);
		dc.add(Example.create(ypxxQuery));
		List<Ypxx> list = (List<Ypxx>) this.getHibernateTemplate().findByCriteria(dc);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
}