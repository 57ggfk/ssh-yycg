package com.easy.echarts;

import java.util.List;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;

/**
 * 自定义数据封装类
 * @author 王杰
 *
 */
public class EasyEnhancedOption extends GsonOption {

	
	/**
	 * 进行一些初始化操作
	 * @param titleName 主标题
	 * @param subtextName 副标题
	 */
	public EasyEnhancedOption(String titleName,String subtextName) {
		//this.legend("采购量");
		this.yAxis(new ValueAxis());//设置数据类型value
		this.title().text(titleName).subtext(subtextName);//设置标题
		this.tooltip().trigger(Trigger.axis);//设置鼠标移入数据柱中提示框
		this.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);//初始化工具箱
		this.calculable(true);//设置calculable值
	}
	
	/**
	 * 设置X轴数据
	 * @param list
	 * @return
	 */
	public Option xList(List<Object> xList){
		//原方法接收一个数组,转换
		Object[] array = xList.toArray();
		this.xAxis(new CategoryAxis().data(array));
		return this;
	}

	/**
	 * 设置Bar
	 * @param easyBar 
	 * @return
	 */
	public Option setBar(EasyBar ... easyBar){
		this.series(easyBar);
		return this;
		
	}
	
	
}
