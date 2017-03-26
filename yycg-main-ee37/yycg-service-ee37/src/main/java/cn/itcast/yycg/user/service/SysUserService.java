package cn.itcast.yycg.user.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysUser;
import cn.itcast.yycg.user.pojo.SysUserCustom;
import cn.itcast.yycg.user.pojo.SysUserQueryCustom;

public interface SysUserService {
	
	public SysUser findById(String id);

	public int getTotalReccord(SysUserQueryCustom sysUserQueryCustom);

	public List<SysUser> findAll(SysUserQueryCustom sysUserQueryCustom, int startIndex, int rows);

	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(String id);
	
	/**
	 * 用户添加
	 * @param sysUserCustom
	 */
	public void save(SysUserCustom sysUserCustom);

	/**
	 * 用户更新
	 * @param sysUserCustom
	 */
	public void updateUser(SysUserCustom sysUserCustom);

	/**
	 * 通过用户名查询用户
	 */
	public SysUser findByUsercode(String usercode);

	/**
	 * 查询用户的所有权限
	 * @param id
	 * @return
	 */
	public List<SysPermission> findAllPermission(String id);
	
	/**
	 * 修改用户密码的方法
	 * @param oldPassword
	 * @param newPassword
	 * @param relPassword
	 * @param userId 
	 */
	public void updatePwd(String oldPassword, String newPassword, String relPassword, String userId);
}
