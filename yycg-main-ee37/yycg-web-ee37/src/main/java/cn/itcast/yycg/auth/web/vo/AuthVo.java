package cn.itcast.yycg.auth.web.vo;

import cn.itcast.yycg.domain.po.SysConf;

public class AuthVo {
	
	private String usercode;
	private String pwd;
	private String validateCode;
	private Integer page = 1;
	private Integer rows = 20;
	private SysConf sysConf;
	private String[] sysConfId;
	private String[] sysConfValue;
	
	public String[] getSysConfId() {
		return sysConfId;
	}
	public void setSysConfId(String[] sysConfId) {
		this.sysConfId = sysConfId;
	}
	public String[] getSysConfValue() {
		return sysConfValue;
	}
	public void setSysConfValue(String[] sysConfValue) {
		this.sysConfValue = sysConfValue;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public SysConf getSysConf() {
		return sysConf;
	}
	public void setSysConf(SysConf sysConf) {
		this.sysConf = sysConf;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
