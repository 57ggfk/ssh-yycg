package cn.itcast.yycg.user.web.vo;

import java.util.List;

import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.user.pojo.SysUserCustom;
import cn.itcast.yycg.user.pojo.SysUserQueryCustom;

public class SysUserVo {

	//分页数据
	private int page = 1;
	private int rows = 15;
	//用户密码
	private String oldPassword;
	private String newPassword;
	private String relPassword;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//回显用户类型，从字典中查询数据
	private List<SysDictInfo> userGroupList;
	//回想用户状态，从字典中查询
	private List<SysDictInfo> userStateList;
	
	public List<SysDictInfo> getUserStateList() {
		return userStateList;
	}
	public void setUserStateList(List<SysDictInfo> userStateList) {
		this.userStateList = userStateList;
	}
	public List<SysDictInfo> getUserGroupList() {
		return userGroupList;
	}
	public void setUserGroupList(List<SysDictInfo> userGroupList) {
		this.userGroupList = userGroupList;
	}

	//自定义对象，用于封装和回显表单数据
	private SysUserCustom sysUserCustom;
	//自定义查询对象，用于封装查询数据
	private SysUserQueryCustom sysUserQueryCustom;
	public SysUserCustom getSysUserCustom() {
		return sysUserCustom;
	}
	public void setSysUserCustom(SysUserCustom sysUserCustom) {
		this.sysUserCustom = sysUserCustom;
	}
	public SysUserQueryCustom getSysUserQueryCustom() {
		return sysUserQueryCustom;
	}
	public void setSysUserQueryCustom(SysUserQueryCustom sysUserQueryCustom) {
		this.sysUserQueryCustom = sysUserQueryCustom;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRelPassword() {
		return relPassword;
	}
	public void setRelPassword(String relPassword) {
		this.relPassword = relPassword;
	}
	
}
