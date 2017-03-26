package cn.itcast.yycg.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.domain.po.SysUser;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.user.dao.SysUserDao;
import cn.itcast.yycg.user.dao.query.SysUserQuery;
import cn.itcast.yycg.user.pojo.SysUserCustom;
import cn.itcast.yycg.user.pojo.SysUserQueryCustom;
import cn.itcast.yycg.user.service.SysUserService;
import cn.itcast.yycg.util.MD5;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public SysUser findById(String id) {
		return sysUserDao.findById(id);
	}

	@Override
	public int getTotalReccord(SysUserQueryCustom sysUserQueryCustom) {
		SysUserQuery sysUserQuery = sysUserQueryCustom;
		return sysUserDao.getTotalRecord(sysUserQuery);
	}

	@Override
	public List<SysUser> findAll(SysUserQueryCustom sysUserQueryCustom, int startIndex, int rows) {

		SysUserQuery sysUserQuery = sysUserQueryCustom;
		return sysUserDao.findAll(sysUserQuery, startIndex, rows);
	}

	@Override
	public void save(SysUserCustom sysUserCustom) {
		// 先查询用户是否存在
		String usercode = sysUserCustom.getUsercode();
		SysUser existSysUser = sysUserDao.findByUsercode(usercode);
		if (existSysUser != null) {
			ResultUtil.throwExcepionResult("添加失败，用户名已被占用");
		}
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserCustom, sysUser);
		// 密码md5加密
		sysUser.setPwd(new MD5().getMD5ofStr(sysUser.getPwd()));
		sysUser.setCreatetime(new Date());

		// 给用户分配角色
		SysRole sysRole = new SysRole();
		sysRole.setId(sysUser.getSysDictInfoByGroupid().getId());
		sysUser.getSysRoles().add(sysRole);
		sysUserDao.save(sysUser);
	}

	@Override
	public void delete(String id) {
		// 先查询用户是否存在
		SysUser sysUser = sysUserDao.findById(id);
		if (sysUser == null) {
			// throw new RuntimeException("要删除的用户不存在");
			ResultUtil.throwExcepionResult("删除失败，要删除的用户不存在！");
		}
		// 删除用户
		sysUserDao.delete(sysUser);
	}

	@Override
	public void updateUser(SysUserCustom sysUserCustom) {
		// 先查询
		SysUser sysUser = sysUserDao.findById(sysUserCustom.getId());
		SysUser existSysUser = sysUserDao.findByUsercode(sysUserCustom.getUsercode());
		if (existSysUser != null && existSysUser != sysUser) {
			// 如果用户名被占用，并且不是自己
			ResultUtil.throwExcepionResult("添加失败，用户名已被占用");
		}
		// 拷贝属性测试1
		// BeanUtils.copyProperties(sysUserCustom, sysUser,
		// new
		// String[]{"contact","addr","email","remark","createtime","sex","phone","movephone","fax","lastupdate"});
		// 拷贝属性测试2
		/*
		 * try { new BeanUtilsBean(){
		 * 
		 * @Override p-blic void copyProperty(Object bean, String name, Object
		 * value) throws IllegalAccessException
		 * ,java.lang.reflect.InvocationTargetException { if (value==null){
		 * return; } super.copyProperty(bean, name, value); };
		 * }.copyProperties(sysUser, sysUserCustom);; } catch (Exception e) {
		 * ResultUtil.throwExcepionResult(e.getMessage()); }
		 */

		// 判断密码字段
		String pwd = sysUserCustom.getPwd();
		if (StringUtils.isBlank(pwd)) {
			// 如果密码为空，保留原来密码
			pwd = sysUser.getPwd();
		} else {
			// 密码md5加密
			pwd = new MD5().getMD5ofStr(pwd);
		}
		BeanUtils.copyProperties(sysUserCustom, sysUser, new String[] { "createtime" });
		sysUser.setPwd(pwd);
	}

	@Override
	public SysUser findByUsercode(String usercode) {
		return sysUserDao.findByUsercode(usercode);
	}

	@Override
	public List<SysPermission> findAllPermission(String id) {

		SysUser sysUser = sysUserDao.findById(id);
		Set<SysRole> sysRoles=sysUser.getSysRoles();
		
		TreeSet<SysPermission> treeRole = new TreeSet<SysPermission>();

		List<SysPermission> allPerm = new ArrayList<SysPermission>();
		for (SysRole sysRole : sysRoles) {
			allPerm.addAll(sysRole.getSysPermissions());
		}
		// 用treeSet集合自动排序的功能，进行排序，需要在SysPermission类中实现comparable接口
		treeRole.addAll(allPerm);
		// 显示所有的权限：
		allPerm.clear();
		allPerm.addAll(treeRole);
/*
		// 用户 -- 角色 -- 权限
		SysUser sysUser = sysUserDao.findById(id);
		// 获得用户所有角色
		Set<SysRole> allRole = sysUser.getSysRoles();

		List<SysPermission> allPerm = new ArrayList<SysPermission>();
		// 获得每个角色的所有权限
		for (SysRole sysRole : allRole) {
			allPerm.addAll(sysRole.getSysPermissions());
		}*/
		return allPerm;
	}

	/**
	 * 业务层修改用户密码的方法
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param relPassword
	 */
	@Override
	public void updatePwd(String id, String oldPassword, String newPassword, String relPassword) {
		// 校验未修改前密码是否为空
		if ("".equals(oldPassword) || oldPassword == null) {
			ResultUtil.throwExcepionResult("旧密码不能为空!");
		}
		// 校验未新密码是否为空
		if ("".equals(newPassword) || newPassword == null) {
			ResultUtil.throwExcepionResult("新密码不能为空!");
		}
		// 校验确认密码是否为空
		if ("".equals(relPassword) || relPassword == null) {
			ResultUtil.throwExcepionResult("确认密码不能为空!");
		}
		// 校验两次输入的密码是否一致
		if (!newPassword.equals(relPassword)) {
			ResultUtil.throwExcepionResult("新密码与确认密码不一致!");
		}
		// 通过用户id获得用户的信息
		SysUser sysUser = sysUserDao.findById(id);
		// 通过用户信息获取用户修改前的密码
		String pwd = sysUser.getPwd();
		// 将传过来的旧密码作加密处理
		String oldPwd = new MD5().getMD5ofStr(oldPassword);
		// 校验输入的旧密码与数据库中密码是否一致
		if (!oldPwd.equals(pwd)) {
			ResultUtil.throwExcepionResult("输入的旧密码不正确!");
		}
		// 将传过来的新密码进行加密处理
		String newPwd = new MD5().getMD5ofStr(newPassword);
		// 校验新的密码与旧的密码是否一致
		if (newPwd.equals(pwd)) {
			ResultUtil.throwExcepionResult("输入的密码无更改!");
		}
		// 将新的加密后的密码设置到用户信息中
		sysUser.setPwd(newPwd);
		// 修改
		sysUserDao.update(sysUser);
	}

}
