package cn.itcast.yycg.cgd.dao.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.cgd.dao.YycgdDao;
import cn.itcast.yycg.cgd.dao.query.YycgdQuery;
import cn.itcast.yycg.domain.po.SysArea;
import cn.itcast.yycg.domain.po.Yycgd;

@Repository
public class YycgdDaoImpl extends BaseDaoImpl<Yycgd> implements YycgdDao {
	
	@Override
	protected DetachedCriteria getDetachedCriteria(Yycgd t) {
		DetachedCriteria dc = super.getDetachedCriteria(t);
		YycgdQuery query = (YycgdQuery) t;
		
		//判断是否有状态参数
		boolean hasZt = query.getSysDictInfoByzt()!=null && StringUtils.isNotBlank(query.getSysDictInfoByzt().getId());
		
		if (query.getDwWss()!=null) {					//卫生室
			dc.add(Restrictions.eq("dwWss", query.getDwWss()));
		} else if (query.getDwGys()!=null) {			//供应商
			dc.createCriteria("dwGys").add(Restrictions.idEq(query.getDwGys().getId())); //功能同上，写法不同
			// 供应商只能查看状态为已审核01003和已受理01005的采购单
			if (!hasZt) {
				dc.add(Restrictions.or(
						Restrictions.eq("sysDictInfoByzt.id", "01003"),
						Restrictions.eq("sysDictInfoByzt.id", "01005")
					));
			}
		} else {										//卫生院或卫生局
			SysArea sysArea = query.getSysArea();
			//加入子查询dwWss再加入子查询sysArea，地区id模糊匹配卫生局或卫生院的地区码wssDq
			dc.createCriteria("dwWss").createCriteria("sysArea").add(Restrictions.like("id", sysArea.getId(), MatchMode.START));
			//卫生院或卫生局 只能够查询 提交未审核 01002和审核未通过01004两种状态
			if (!hasZt) {
				dc.add(Restrictions.or(
						Restrictions.eq("sysDictInfoByzt.id", "01002"),
						Restrictions.eq("sysDictInfoByzt.id", "01003")
					));
			}
		}
		
		//采购单状态00101
		if (hasZt) {
			dc.add(Restrictions.eq("sysDictInfoByzt.id", query.getSysDictInfoByzt().getId()));
		}
				
		//封装其它查询条件，采购单名称
		dc.add(getExample(t));
		
		//根据采购单编号id精确 查询
		Integer id = t.getId();
		if (id!=null && id!=0) {
			dc.add(Restrictions.idEq(id));
		}
		return dc;
	}
	
}
