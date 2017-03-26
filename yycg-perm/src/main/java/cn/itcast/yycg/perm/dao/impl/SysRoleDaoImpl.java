package cn.itcast.yycg.perm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.perm.dao.SysRoleDao;
import cn.itcast.yycg.perm.dao.query.SysRoleQuery;

@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao {
	
	private DetachedCriteria getCondition(SysRoleQuery sysRoleQuery){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysRole.class);
		
		if(sysRoleQuery != null) {
			
		}
		
		return detachedCriteria;
	}
	

	@Override
	public int getTotalRecord(SysRoleQuery sysRoleQuery) {
		DetachedCriteria detachedCriteria = getCondition(sysRoleQuery);
		detachedCriteria.setProjection(Projections.rowCount());
		
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return list.get(0).intValue();
	}

	@Override
	public List<SysRole> findAll(SysRoleQuery sysRoleQuery, int firstResult, int maxResults) {
		DetachedCriteria detachedCriteria = getCondition(sysRoleQuery);
		
		return (List<SysRole>) this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
	}

}
