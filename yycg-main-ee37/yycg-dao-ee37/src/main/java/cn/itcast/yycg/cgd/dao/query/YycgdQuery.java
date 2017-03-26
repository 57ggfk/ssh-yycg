package cn.itcast.yycg.cgd.dao.query;

import cn.itcast.yycg.domain.po.SysArea;
import cn.itcast.yycg.domain.po.Yycgd;

public class YycgdQuery extends Yycgd {
	
	private SysArea sysArea;  //卫生室或卫生局地区

	public SysArea getSysArea() {
		return sysArea;
	}

	public void setSysArea(SysArea sysArea) {
		this.sysArea = sysArea;
	}

}
