package cn.itcast.yycg.perm.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.perm.pojo.SysPermissionCustom;
import cn.itcast.yycg.perm.pojo.SysPermCustom;
import cn.itcast.yycg.perm.pojo.SysPermQueryCustom;

public interface SysPermissionService {

	List<SysPermission> findAll();

	List<SysPermission> findAllByRoleId(String roleId);

	void updatePermission(String roleId, List<String> addPermIds);

	int findChildCount(String parentId);

	void savePermission(SysPermissionCustom sysPermissionCustom);

	void update(SysPermissionCustom sysPermissionCustom);

	void delete(SysPermission sysPermission);

	int findCount(SysPermQueryCustom sysPermQueryCustom);

	List<SysPermission> findAllList(SysPermQueryCustom sysPermQueryCustom, int startIndex, int pageSize);

	SysPermission findById(String id);

	void updateAll(SysPermission sysPermission);
	/**
	 * 删除用户
	 * @param id
	 */
	void deleteById(String id);
	/**
	 * 权限添加数据
	 * @param allData
	 */
	void addData( String[] allData);

}
