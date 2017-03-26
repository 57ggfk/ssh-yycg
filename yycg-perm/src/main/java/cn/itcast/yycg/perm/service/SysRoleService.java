package cn.itcast.yycg.perm.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.perm.pojo.SysRoleQueryCustom;

public interface SysRoleService {

	int getTotalRecord(SysRoleQueryCustom sysRoleQueryCustom);

	List<SysRole> findAll(SysRoleQueryCustom sysRoleQueryCustom, int firstResult, int maxResults);

	SysRole findById(String id);

}
