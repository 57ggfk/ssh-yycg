package cn.itcast.yycg.cgd.web.vo;

import java.util.List;

import cn.itcast.yycg.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.cgd.pojo.YycgdMxCustom;
import cn.itcast.yycg.cgd.pojo.YycgdMxQueryCustom;
import cn.itcast.yycg.cgd.pojo.YycgdQueryCustom;
import cn.itcast.yycg.domain.po.SysDictInfo;

public class YycgdVo {

	// 分页
	private int page = 1;
	private int rows = 10;

	// 药品类别001
	private List<SysDictInfo> yplbList;
	// 交易状态002
	private List<SysDictInfo> jyztList;
	// 质量层次003
	private List<SysDictInfo> ypzlccList;

	//采购状态
	private List<SysDictInfo> cgztList;
	//供货状态
	private List<SysDictInfo> ghztList;

	// 采购单

	private YycgdCustom yycgdCustom;
	private YycgdQueryCustom yycgdQueryCustom;

	// 采购单状态
	private List<SysDictInfo> yycgdZtList;

	// 采购单明细
	private YycgdMxCustom yycgdMxCustom;
	private YycgdMxQueryCustom yycgdMxQueryCustom;

	// 医药采购单明细药品信息ids
	private String[] ypxxids;

	// 医药采购单明细ids
	private String[] yycgdmxIds;
	// 医药采购单明细采购量数组，与采购单ids对应
	private Integer[] yycgdmxCgls;

	// 药品发货状态
	private String[] yycgdmxztIds;

	public String[] getYycgdmxztIds() {
		return yycgdmxztIds;
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

	public List<SysDictInfo> getYplbList() {
		return yplbList;
	}

	public void setYplbList(List<SysDictInfo> yplbList) {
		this.yplbList = yplbList;
	}

	public List<SysDictInfo> getJyztList() {
		return jyztList;
	}

	public void setJyztList(List<SysDictInfo> jyztList) {
		this.jyztList = jyztList;
	}

	public List<SysDictInfo> getYpzlccList() {
		return ypzlccList;
	}

	public void setYpzlccList(List<SysDictInfo> ypzlccList) {
		this.ypzlccList = ypzlccList;
	}

	public List<SysDictInfo> getCgztList() {
		return cgztList;
	}

	public void setCgztList(List<SysDictInfo> cgztList) {
		this.cgztList = cgztList;
	}

	public List<SysDictInfo> getGhztList() {
		return ghztList;
	}

	public void setGhztList(List<SysDictInfo> ghztList) {
		this.ghztList = ghztList;
	}

	public YycgdCustom getYycgdCustom() {
		return yycgdCustom;
	}

	public void setYycgdCustom(YycgdCustom yycgdCustom) {
		this.yycgdCustom = yycgdCustom;
	}

	public YycgdQueryCustom getYycgdQueryCustom() {
		return yycgdQueryCustom;
	}

	public void setYycgdQueryCustom(YycgdQueryCustom yycgdQueryCustom) {
		this.yycgdQueryCustom = yycgdQueryCustom;
	}

	public List<SysDictInfo> getYycgdZtList() {
		return yycgdZtList;
	}

	public void setYycgdZtList(List<SysDictInfo> yycgdZtList) {
		this.yycgdZtList = yycgdZtList;
	}

	public YycgdMxCustom getYycgdMxCustom() {
		return yycgdMxCustom;
	}

	public void setYycgdMxCustom(YycgdMxCustom yycgdMxCustom) {
		this.yycgdMxCustom = yycgdMxCustom;
	}

	public YycgdMxQueryCustom getYycgdMxQueryCustom() {
		return yycgdMxQueryCustom;
	}

	public void setYycgdMxQueryCustom(YycgdMxQueryCustom yycgdMxQueryCustom) {
		this.yycgdMxQueryCustom = yycgdMxQueryCustom;
	}

	public String[] getYpxxids() {
		return ypxxids;
	}

	public void setYpxxids(String[] ypxxids) {
		this.ypxxids = ypxxids;
	}

	public String[] getYycgdmxIds() {
		return yycgdmxIds;
	}

	public void setYycgdmxIds(String[] yycgdmxIds) {
		this.yycgdmxIds = yycgdmxIds;
	}

	public Integer[] getYycgdmxCgls() {
		return yycgdmxCgls;
	}

	public void setYycgdmxCgls(Integer[] yycgdmxCgls) {
		this.yycgdmxCgls = yycgdmxCgls;
	}

	public void setYycgdmxztIds(String[] yycgdmxztIds) {
		this.yycgdmxztIds = yycgdmxztIds;
	}

}
