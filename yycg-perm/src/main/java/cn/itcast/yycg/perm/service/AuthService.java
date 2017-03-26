package cn.itcast.yycg.perm.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysUser;

public interface AuthService {

	/**
	 * 通过账号查询用户
	 * @param usercode
	 * @return
	 */
	SysUser findUserByUsercode(String usercode);

	/**
	 * 获得指定用户对应所有的权限
	 * @param userId
	 * @return
	 */
	List<SysPermission> findAllPermission(String userId);

}
