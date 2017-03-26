package com.easy.echarts;

import com.github.abel533.echarts.series.Bar;

/**
 * EasyBar是一个Bar的子类
 * @author 王杰
 *
 */
public class EasyBar extends Bar{

	public EasyBar(String barName){
		super(barName);
		//this.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
		//this.markLine().data(new PointData().type(MarkType.average).name("平均值"));
		//this.markPoint().data(new PointData("年最高", 182.2).xAxis(7).yAxis(183).symbolSize(18), new PointData("年最低", 2.3).xAxis(11).yAxis(3));
	}
	
}
