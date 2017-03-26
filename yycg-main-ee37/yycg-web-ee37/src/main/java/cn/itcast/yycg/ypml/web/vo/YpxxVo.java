package cn.itcast.yycg.ypml.web.vo;

import java.io.File;
import java.util.List;

import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.ypml.pojo.YpxxCustom;
import cn.itcast.yycg.ypml.pojo.YpxxQueryCustom;

public class YpxxVo {
	
	private YpxxCustom ypxxCustom;
	private YpxxQueryCustom ypxxQueryCustom;
	
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
	//药品类别列表
	private List<SysDictInfo> yplbList;
	//药品交易状态列表
	private List<SysDictInfo> ypjyztList;
	
	//上传的药品信息
	private File ypxximportfile;
	private String ypxximportfileContentType;
	private String ypxximportfileFileName;
	
	
	public File getYpxximportfile() {
		return ypxximportfile;
	}
	public void setYpxximportfile(File ypxximportfile) {
		this.ypxximportfile = ypxximportfile;
	}
	public String getYpxximportfileContentType() {
		return ypxximportfileContentType;
	}
	public void setYpxximportfileContentType(String ypxximportfileContentType) {
		this.ypxximportfileContentType = ypxximportfileContentType;
	}
	public String getYpxximportfileFileName() {
		return ypxximportfileFileName;
	}
	public void setYpxximportfileFileName(String ypxximportfileFileName) {
		this.ypxximportfileFileName = ypxximportfileFileName;
	}
	public List<SysDictInfo> getYplbList() {
		return yplbList;
	}
	public void setYplbList(List<SysDictInfo> yplbList) {
		this.yplbList = yplbList;
	}
	
	public List<SysDictInfo> getYpjyztList() {
		return ypjyztList;
	}
	public void setYpjyztList(List<SysDictInfo> ypjyztList) {
		this.ypjyztList = ypjyztList;
	}
	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}
	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}
	public YpxxQueryCustom getYpxxQueryCustom() {
		return ypxxQueryCustom;
	}
	public void setYpxxQueryCustom(YpxxQueryCustom ypxxQueryCustom) {
		this.ypxxQueryCustom = ypxxQueryCustom;
	}
	
}
