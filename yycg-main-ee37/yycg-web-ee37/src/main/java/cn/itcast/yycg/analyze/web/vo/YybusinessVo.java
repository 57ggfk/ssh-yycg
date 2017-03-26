package cn.itcast.yycg.analyze.web.vo;

import cn.itcast.yycg.analyze.pojo.YybusinessCustom;
import cn.itcast.yycg.analyze.pojo.YybusinessQueryCustom;

public class YybusinessVo {
	
	//分页
	private int page = 1;
	private int rows = 10;
	
	
	
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

	private String jfreechart_filename;

	private YybusinessCustom yybusinessCustom;
	
	private YybusinessQueryCustom yybusinessQueryCustom;
	
	
	
	
	public YybusinessCustom getYybusinessCustom() {
		return yybusinessCustom;
	}

	public void setYybusinessCustom(YybusinessCustom yybusinessCustom) {
		this.yybusinessCustom = yybusinessCustom;
	}

	public YybusinessQueryCustom getYybusinessQueryCustom() {
		return yybusinessQueryCustom;
	}

	public void setYybusinessQueryCustom(YybusinessQueryCustom yybusinessQueryCustom) {
		this.yybusinessQueryCustom = yybusinessQueryCustom;
	}

	public String getJfreechart_filename() {
		return jfreechart_filename;
	}

	public void setJfreechart_filename(String jfreechart_filename) {
		this.jfreechart_filename = jfreechart_filename;
	}

	
}
