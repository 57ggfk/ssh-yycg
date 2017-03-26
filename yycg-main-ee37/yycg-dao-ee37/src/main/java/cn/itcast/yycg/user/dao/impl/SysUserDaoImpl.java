package cn.itcast.yycg.user.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.SysUser;
import cn.itcast.yycg.user.dao.SysUserDao;
import cn.itcast.yycg.user.dao.query.SysUserQuery;

@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao {
	@Override
	protected DetachedCriteria getDetachedCriteria(SysUser t) {
		DetachedCriteria dc = super.getDetachedCriteria(t);
		if (t==null) {
			return dc;
		}
		//准备条件
		SysUserQuery sysUserQuery = (SysUserQuery) t;
//		String usercode = sysUserQuery.getUsercode();
//		String username = sysUserQuery.getUsername();
		SysDictInfo group = sysUserQuery.getSysDictInfoByGroupid();
		//封装条件
		dc.add(Example.create(sysUserQuery).enableLike(MatchMode.ANYWHERE).ignoreCase());
		if (group!=null && StringUtils.isNotBlank(group.getId())) {
			System.out.println("groupid"+group.getId());
			dc.add(Restrictions.eq("sysDictInfoByGroupid", group));
		}
		return dc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SysUser findByUsercode(String usercode) {
		 List<SysUser> list = (List<SysUser>) this.getHibernateTemplate().find("from "+SysUser.class.getName()+" where usercode=?", usercode);
		 if (list!=null && list.size()>0) {
			 return list.get(0);
		 }
		return null;
	}
}
