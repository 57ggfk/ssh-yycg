package cn.itcast.yycg.perm.dao;

import java.util.List;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.perm.dao.query.SysRoleQuery;
import cn.itcast.yycg.perm.pojo.SysPermQueryCustom;

public interface SysRoleDao extends BaseDao<SysRole> {

	int getTotalRecord(SysRoleQuery sysRoleQuery);

	List<SysRole> findAll(SysRoleQuery sysRoleQuery, int firstResult, int maxResults);
	
}
