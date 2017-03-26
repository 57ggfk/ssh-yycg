package cn.itcast.yycg.analyze.dao.query;

import cn.itcast.yycg.domain.po.DwWsy;
import cn.itcast.yycg.domain.po.Yybusiness;

public class YybusinessQuery extends Yybusiness {
	//开始日期
	private String startDate;
	//结束日期
	private String endDate;
	//地区id
	private String sysAreaId;
	//卫生院
	private DwWsy dwWsy;
	
	public DwWsy getDwWsy() {
		return dwWsy;
	}
	public void setDwWsy(DwWsy dwWsy) {
		this.dwWsy = dwWsy;
	}
	//按年条件查询的字段
	private String year;
	
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	//判断是供应商登录,还是卫生院登录,还是卫生院登录
	private String dw;
	//身份id  
	private String dwId;
	
	

	/**
	 * @return the dwId
	 */
	public String getDwId() {
		return dwId;
	}
	/**
	 * @param dwId the dwId to set
	 */
	public void setDwId(String dwId) {
		this.dwId = dwId;
	}
	/**
	 * @return the dw
	 */
	public String getDw() {
		return dw;
	}
	/**
	 * @param dw the dw to set
	 */
	public void setDw(String dw) {
		this.dw = dw;
	}
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @return the sysAreaId
	 */
	public String getSysAreaId() {
		return sysAreaId;
	}
	/**
	 * @param sysAreaId the sysAreaId to set
	 */
	public void setSysAreaId(String sysAreaId) {
		this.sysAreaId = sysAreaId;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}
