package cn.itcast.yycg.perm.web.vo;

import java.util.List;

import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.perm.pojo.SysPermissionCustom;
import cn.itcast.yycg.perm.pojo.SysPermCustom;
import cn.itcast.yycg.perm.pojo.SysPermQueryCustom;
import cn.itcast.yycg.perm.pojo.SysRoleCustom;
import cn.itcast.yycg.perm.pojo.SysRoleQueryCustom;

public class PermVo {
	
	private int page;
	private int rows;
	
	private SysRoleCustom sysRoleCustom;
	private SysRoleQueryCustom sysRoleQueryCustom;
	
	private List<String> addPermIds;	//授予权限
	private List<String> delPermIds;	//取消权限
	
	private SysRole sysRole;
	
	private SysPermissionCustom sysPermissionCustom;

	private String allDataString;

	public String getAllDataString() {
		return allDataString;
	}
	public void setAllDataString(String allDataString) {
		this.allDataString = allDataString;
	}
	private SysPermQueryCustom sysPermQueryCustom;
	private SysPermCustom sysPermCustom;
	
	public SysPermQueryCustom getSysPermQueryCustom() {
		return sysPermQueryCustom;
	}
	public void setSysPermQueryCustom(SysPermQueryCustom sysPermQueryCustom) {
		this.sysPermQueryCustom = sysPermQueryCustom;
	}
	public SysPermCustom getSysPermCustom() {
		return sysPermCustom;
	}
	public void setSysPermCustom(SysPermCustom sysPermCustom) {
		this.sysPermCustom = sysPermCustom;
	}

	public SysRoleCustom getSysRoleCustom() {
		return sysRoleCustom;
	}
	public void setSysRoleCustom(SysRoleCustom sysRoleCustom) {
		this.sysRoleCustom = sysRoleCustom;
	}
	public SysRoleQueryCustom getSysRoleQueryCustom() {
		return sysRoleQueryCustom;
	}
	public void setSysRoleQueryCustom(SysRoleQueryCustom sysRoleQueryCustom) {
		this.sysRoleQueryCustom = sysRoleQueryCustom;
	}
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
	public List<String> getAddPermIds() {
		return addPermIds;
	}
	public void setAddPermIds(List<String> addPermIds) {
		this.addPermIds = addPermIds;
	}
	public List<String> getDelPermIds() {
		return delPermIds;
	}
	public void setDelPermIds(List<String> delPermIds) {
		this.delPermIds = delPermIds;
	}
	public SysRole getSysRole() {
		return sysRole;
	}
	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}
	public SysPermissionCustom getSysPermissionCustom() {
		return sysPermissionCustom;
	}
	public void setSysPermissionCustom(SysPermissionCustom sysPermissionCustom) {
		this.sysPermissionCustom = sysPermissionCustom;
	}

}
