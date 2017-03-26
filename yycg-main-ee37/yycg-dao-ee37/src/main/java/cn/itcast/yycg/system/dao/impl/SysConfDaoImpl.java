package cn.itcast.yycg.system.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.SysConf;
import cn.itcast.yycg.system.dao.SysConfDao;
@Repository
public class SysConfDaoImpl extends BaseDaoImpl<SysConf> implements SysConfDao {
	private DetachedCriteria getCondition(SysConf sysConf){
		DetachedCriteria dc = DetachedCriteria.forClass(SysConf.class);
		return dc;
	}
	@Override
	//获取信息总条数
	public Integer findCount(SysConf sysConf) {
		DetachedCriteria condition = this.getCondition(sysConf);
		condition.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(condition);
		if(list.size()>0){
			return list.get(0).intValue();
		}
		return null;
	}
	//分页获取系统信息的详细信息
	@Override
	public List<SysConf> findAll(SysConf sysConf, Integer firstResult, Integer maxResults) {
		DetachedCriteria condition = this.getCondition(sysConf);
		
		return (List<SysConf>) this.getHibernateTemplate().findByCriteria(condition, firstResult, maxResults);
	}

	
}
