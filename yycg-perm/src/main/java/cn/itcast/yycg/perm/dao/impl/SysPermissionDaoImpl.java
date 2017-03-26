package cn.itcast.yycg.perm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.perm.dao.SysPermissionDao;
import cn.itcast.yycg.perm.pojo.SysPermQueryCustom;

@Repository
public class SysPermissionDaoImpl extends BaseDaoImpl<SysPermission> implements SysPermissionDao {

//	@Override
//	public List<SysPermission> findAllByRoleId(String roleId) {
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysPermission.class);
//		detachedCriteria.add(Restrictions.eq("", roleId));
//		return (List<SysPermission>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
//	}
	private DetachedCriteria getCondition(SysPermQueryCustom sysPermQueryCustom){
		DetachedCriteria dc = DetachedCriteria.forClass(SysPermission.class);
		return dc;
	}
	@Override
	public List<SysPermission> findAll() {
		return (List<SysPermission>) this.getHibernateTemplate().find("from SysPermission order by id");
	}

	@Override
	public int findChildCount(String parentId) {
		// TODO Auto-generated method stub
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysPermission.class);
		detachedCriteria.add(Restrictions.eq("parentid", parentId));
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return list.get(0).intValue();
		
	}

	@Override
	public int findCount(SysPermQueryCustom sysPermQueryCustom) {
		DetachedCriteria condition = this.getCondition(sysPermQueryCustom);
		condition.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(condition);
		if(list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}

	@Override
	public List<SysPermission> findAllList(SysPermQueryCustom sysPermQueryCustom, int firstResult, int maxsResult) {
		DetachedCriteria condition = this.getCondition(sysPermQueryCustom);
		return (List<SysPermission>) this.getHibernateTemplate().findByCriteria(condition, firstResult, maxsResult);
	}
}
