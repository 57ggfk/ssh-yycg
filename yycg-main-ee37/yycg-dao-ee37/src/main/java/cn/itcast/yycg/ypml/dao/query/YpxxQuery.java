package cn.itcast.yycg.ypml.dao.query;

import cn.itcast.yycg.domain.po.Ypxx;

public class YpxxQuery extends Ypxx{
	
	//价格范围
	private Float zbjglower;
	private Float zbjgupper;
	public Float getZbjglower() {
		return zbjglower;
	}
	public void setZbjglower(Float zbjglower) {
		this.zbjglower = zbjglower;
	}
	public Float getZbjgupper() {
		return zbjgupper;
	}
	public void setZbjgupper(Float zbjgupper) {
		this.zbjgupper = zbjgupper;
	}
	
	
}
