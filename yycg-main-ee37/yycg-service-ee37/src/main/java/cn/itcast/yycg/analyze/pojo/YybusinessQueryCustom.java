package cn.itcast.yycg.analyze.pojo;

import cn.itcast.yycg.analyze.dao.query.YybusinessQuery;
import cn.itcast.yycg.domain.po.DwWsy;

public class YybusinessQueryCustom extends YybusinessQuery {

	private DwWsy dwWsy = new DwWsy();

	public DwWsy getDwWsy() {
		return dwWsy;
	}

	public void setDwWsy(DwWsy dwWsy) {
		this.dwWsy = dwWsy;
	}
	
	
}
