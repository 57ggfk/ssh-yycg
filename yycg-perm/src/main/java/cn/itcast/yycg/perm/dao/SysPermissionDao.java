package cn.itcast.yycg.perm.dao;

import java.util.List;
import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.perm.pojo.SysPermQueryCustom;
public interface SysPermissionDao extends BaseDao<SysPermission> {

	int findChildCount(String parentId);
	List<SysPermission> findAllList(SysPermQueryCustom sysPermQueryCustom, int firstResult, int maxsResult);
//	List<SysPermission> findAllByRoleId(String roleId);
	public int findCount(SysPermQueryCustom sysPermQueryCustom);
}
