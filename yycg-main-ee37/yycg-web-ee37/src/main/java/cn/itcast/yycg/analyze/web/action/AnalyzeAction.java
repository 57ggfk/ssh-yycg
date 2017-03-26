package cn.itcast.yycg.analyze.web.action;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.option.NoDataLoadingOption;
import com.github.abel533.echarts.series.Bar;

import cn.itcast.yycg.analyze.service.YybusinessService;
import cn.itcast.yycg.analyze.web.vo.YybusinessVo;
import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.Yybusiness;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.util.MyUtil;
import cn.itcast.yycg.util.PageBean;

@Controller
@Scope("prototype")
public class AnalyzeAction extends BaseAction<YybusinessVo> {

	@Autowired
	private YybusinessService yybusinessService;

	// 按照区域统计
	public String sumbyarea() throws IOException {
		// 准备数据，并保存session，将保存session名称存放值栈，jsp页面显示名称

		YybusinessVo yybusinessVo = this.getModel();

		DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
	
		// 从数据库查询数据，将模拟数据替换掉
		List<Map<String, Object>> list = yybusinessService.findSumByDw(yybusinessVo.getYybusinessQueryCustom());
		for (Map<String, Object> map : list) {
			String areaName = (String) map.get("AREANAME");
			Number zje = (Number) map.get("ZJE");
			dataset2.addValue(zje, "药品采购金额", areaName);
		}

		JFreeChart chart = ChartFactory.createBarChart3D("药品采购金额汇总", // 图形名称
				"", // 分类名称，为横坐标名称
				"采购金额(元)", // 值名称，为纵坐标名称
				dataset2, // 数据集合
				PlotOrientation.VERTICAL, // 垂直显示
				true, // 是否显示图例
				false, // 是否使用工具提示
				false);// 是否使用url

		// 在柱上显示数值
		CategoryPlot plot = chart.getCategoryPlot();

		BarRenderer3D renderer = new BarRenderer3D();

		// 设置柱的颜色
		// renderer.setSeriesPaint(0, Color.decode("#ff0000"));

		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		// 默认的数字显示在柱子中，通过如下两句可调整数字的显示
		// 注意：此句很关键，若无此句，数字的显示会被覆盖
		renderer.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(10D);
		// 设置每个地区所包含的平行柱的之间距离
		// renderer.setItemMargin(0.3);
		plot.setRenderer(renderer);
		// 设置字体
		// 配置字体
		Font xfont = new Font("宋体", Font.PLAIN, 12);// X轴
		Font yfont = new Font("宋体", Font.PLAIN, 12);// Y轴
		Font kfont = new Font("宋体", Font.PLAIN, 12);// 底部
		Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题
		// 图形的绘制结构对象,对于饼图不适用
		// CategoryPlot plot = chart.getCategoryPlot();

		// 图片标题
		chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

		// 底部
		LegendTitle legendTitle = chart.getLegend();
		if (legendTitle != null) {
			legendTitle.setItemFont(kfont);
		}

		// X 轴
		org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(xfont);// 轴标题
		domainAxis.setTickLabelFont(xfont);// 轴数值
		domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		// Y 轴
		org.jfree.chart.axis.ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色
		rangeAxis.setTickLabelFont(yfont);

		HttpSession session = ServletActionContext.getRequest().getSession();
		// 将生成的图形写到session，获取到一个图形的名称，此名称和session中图形对应的，在jsp中使用此名称输出图形
		String jfreechart_filename = ServletUtilities.saveChartAsPNG(chart, 900, 500, null, session);

		// 将名称 存到值栈
		yybusinessVo.setJfreechart_filename(jfreechart_filename);
		return "sumbyarea";
	}

	// 获取vo
	private YybusinessVo yybusinessVo = this.getModel();

	/**
	 * 导出excle文件的方法
	 * @author jiang
	 */
	public void export_excel() throws IOException {

		// 获取模板
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			// 通过日期条件查询所有数据
			List<Map<String, Object>> list = yybusinessService.findSumByDw(yybusinessVo.getYybusinessQueryCustom());
			ServletContext servletContext = ServletActionContext.getServletContext();
			String rootpath = servletContext.getRealPath("/");
			fileInputStream = new FileInputStream(rootpath + "/template/statistical_templete.xls");
			// 将输入流转化成workbook对象
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			// 获取工作表
			HSSFSheet sheet = workbook.getSheetAt(0);

			// 将数据库中查询到的数据写入到 excel表格中

			// 创建单元格样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置单元格内容水平居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 设置单元格内容垂直居中
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

			// 将数据库中查询到的数据写入到 excel表格中

			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = list.get(i);
				// 获取村庄名
				String areaName = (String) map.get("AREANAME");
				// 获取金额
				BigDecimal zje = (BigDecimal) map.get("ZJE");
				// 转换成double
				double doubleValue = zje.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				// 获取行 (默认从第三行开始)
				HSSFRow row = sheet.getRow(i + 2);
				if (row == null) {
					row = sheet.createRow(i + 2);
				}
				// 获得单元格设置样式
				// 设置村庄名与采购金额
				HSSFCell cell01 = row.getCell(0);
				if (cell01==null) {
					cell01 = row.createCell(0);
				}
				cell01.setCellStyle(style);
				cell01.setCellValue(areaName);
				
				HSSFCell cell02 = row.getCell(1);
				if (cell02==null) {
					cell02 = row.createCell(1);
				}
				cell02.setCellStyle(style);
				cell02.setCellValue(doubleValue);
				
			}
			// 获取登录用户名称
			ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
			// 文件名称
			String fileName = activeUser.getUsercode() + MyUtil.getFileName();
			// 文件的绝对地址
			String filePath = rootpath + "download/" + fileName + ".xls";
			// 文件的下载地址
			String downloadPath = "/download/" + fileName + ".xls";

			fileOutputStream = new FileOutputStream(filePath);
			workbook.write(fileOutputStream);

			this.write_object(ResultUtil.createSubmitResult(
					"<a href ='" + downloadPath + "'>            点击下载                        </a>"));

		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}

		}

	}

	/**
	 * 12月20日 首次添加 说明:该方法用于统计图表ajax获取option数据 维护人:Wangjie
	 * 
	 * @throws IOException
	 */
	public void AjaxSumbyarea() throws IOException {

		
		// 创建数据承载对象
		GsonOption option = new GsonOption();
		
		// 初始化
		List<Object> xAxisList = new ArrayList<>();
		List<Object> barList = new ArrayList<>();

		// ****准备数据
		List<Map<String, Object>> dataList = yybusinessService.findSumByDw(yybusinessVo.getYybusinessQueryCustom());

		if (dataList!=null) {
			
			// 循环遍历数据添加进option
			for (Map<String, Object> data : dataList) {
				xAxisList.add(data.get("AREANAME"));
				BigDecimal object = (BigDecimal) data.get("ZJE");
				// 可以直接处理BigDecimal小数位的方式
				barList.add(object.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
	
			// ****准备数据
	
			// ******设置基本属性*******start
			// 设置标题和子标题(子标题即主标题下的小文字说明)
			option.title().text("医药采购项目图表统计").subtext("一组");
			// 设置鼠标移入数据柱中提示框,可自行设置
			option.tooltip().trigger(Trigger.axis);
			// 添加图标图例,每个图标仅可有一个图例(就是统计图上方的统计说明)
			option.legend("采购金额");
			// ******设置基本属性*******end
	
			// ******设置柱状图参数属性******* start
			// toolbox:柱状图右上角的工具箱,包括查看数据,以及下载为png等等..
			// 该方法支持链式编程,下面的属性仅供参考,详细资料在API
			option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
					Tool.restore, Tool.saveAsImage);
			// 设置calculable值,该值是什么?不知道
			option.calculable(true);
			// ******设置柱状图参数属性******* end
	
			// ******设置需要统计的数据******* start
			// xAxis:即X轴需要显示的数据,简单来说就是需要统计数据的名称也就是镇的名称
			option.xAxis(new CategoryAxis().data(xAxisList.toArray()));
			// yAxis:即y轴需要显示的数据,简单来说就是数据的数值,如果直接new ValueAxis()
			// 应该是根据数据的需求进行自动显示
			option.yAxis(new ValueAxis());
			// 创建bar对象 构造方法传入的就是该统计值的简称,注意该简称需要对应上面的legend值
			Bar bar = new Bar("采购金额");
			// data:就是该bar的数据,需要和上面的城镇名称进行对应
	
			bar.data(barList.toArray());
			// 设置标注,markPoint是一个标注对象,支持链式编程,可以直接设置值,很多特效可以在这里设置
			bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"),
					new PointData().type(MarkType.min).name("最小值"));
			// 设置标线,也就是横穿数据柱的一条线,代表的是平均值,详情请看MarkType对象
			bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
	
			/*
			 * //当然,这又是一个bar Bar bar2 = new Bar("采购金额"); //这里代表采购金额 List<Double>
			 * list = Arrays.asList(1500.0, 2100.0, 3521.4, 520.36, 1820.6, 1300.5);
			 * bar2.setData(list); bar2.markPoint().data(new PointData("年最高",
			 * 182.2).xAxis(7).yAxis(183).symbolSize(18), new PointData("年最低",
			 * 2.3).xAxis(11).yAxis(3)); //******设置需要统计的数据******* end //显示采购金额平均值
			 * bar2.markLine().data(new
			 * PointData().type(MarkType.average).name("平均值"));
			 */
	
			// ********进行对象封装****
			option.series(bar);
		}else {
			option.setNoDataLoadingOption(new NoDataLoadingOption().text("无数据"));
		}
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().getWriter().write(option.toString());
	}

	/**
	 * Wangjie 创建 该方法用于跳转到drugyzrea.jsp
	 * 
	 * @return
	 */
	public String drugyzreaUI() {

		return "drugyzreaUI";
	}

	/**
	 * 12月20日 18:29首次添加 说明:该方法用于页面使用ajax获取药品统计数据
	 * 页面:/business/analyze/drugyzrea.jsp 维护人:Wangjie
	 */
	public void list_drugyzrea() {
		// 方法返回一个药品信息的集合
		// 查询总记录数
		int total = yybusinessService.getTotalRecordByCount(yybusinessVo.getYybusinessQueryCustom());

		PageBean pageBean = new PageBean(yybusinessVo.getPage(), yybusinessVo.getRows(), total);

		List<Yybusiness> ypxxList = yybusinessService.list_drugyzrea(yybusinessVo.getYybusinessQueryCustom(),
				pageBean.getStartIndex(), pageBean.getPageSize());
		// 准备datagrid数据
		
		
		write_object(ResultUtil.createDataGridResult(total, ypxxList));
	}

	/** 
	 * 该方法用于ajax获取药品统计数据的option
	 *  
	 * @throws IOException 
	 */
	public void ajaxDrugyzrea_bar() throws IOException {
		
		// 创建数据承载对象
		GsonOption option = new GsonOption();
		// 初始化
		List<Object> xAxisList = new ArrayList<>();
		List<Object> barList = new ArrayList<>();
		List<Object> barList2 = new ArrayList<>();

		// ****准备数据,写死了二十条
		List<Yybusiness> ypxxList = yybusinessService.list_drugyzrea(yybusinessVo.getYybusinessQueryCustom(), 0, 10);

		if (ypxxList!=null) {
			
			
			for (Yybusiness yybusiness : ypxxList) {
				
				//对名称进行切割
				String mc = yybusiness.getYpxx().getMc();
				if (mc.length()>4) {
					mc = mc.substring(0, 4);
					mc+="..";
				}
				xAxisList.add(mc);
				barList.add(yybusiness.getCgl());
				BigDecimal cgjeBig = new BigDecimal(yybusiness.getCgje());
				cgjeBig = cgjeBig.setScale(2, BigDecimal.ROUND_HALF_UP);
				barList2.add(cgjeBig.doubleValue());
			}
	
			// ******设置基本属性*******start
			// 设置标题和子标题(子标题即主标题下的小文字说明)
			option.title().text("药品采购数据统计").subtext("一组");
			// 设置鼠标移入数据柱中提示框,可自行设置
			option.tooltip().trigger(Trigger.axis);
			// 添加图标图例,每个图标仅可有一个图例(就是统计图上方的统计说明)
			option.legend("采购量", "采购金额");
	
			option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
					Tool.restore, Tool.saveAsImage);
			// 设置calculable值,该值是什么?不知道
			option.calculable(true);
	
			option.xAxis(new CategoryAxis().data(xAxisList.toArray()));
			// yAxis:即y轴需要显示的数据,简单来说就是数据的数值,如果直接new ValueAxis()
			// 应该是根据数据的需求进行自动显示
			option.yAxis(new ValueAxis());
	
			Bar bar = new Bar("采购量");
			// data:就是该bar的数据,需要和上面的城镇名称进行对应
	
			bar.data(barList.toArray());
			// 设置标注,markPoint是一个标注对象,支持链式编程,可以直接设置值,很多特效可以在这里设置
			bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"),
					new PointData().type(MarkType.min).name("最小值"));
			// 设置标线,也就是横穿数据柱的一条线,代表的是平均值,详情请看MarkType对象
			bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
	
			Bar bar2 = new Bar("采购金额");
			// data:就是该bar的数据,需要和上面的城镇名称进行对应
	
			bar2.data(barList2.toArray());
			// 设置标注,markPoint是一个标注对象,支持链式编程,可以直接设置值,很多特效可以在这里设置
			bar2.markPoint().data(new PointData().type(MarkType.max).name("最大值"),
					new PointData().type(MarkType.min).name("最小值"));
			// 设置标线,也就是横穿数据柱的一条线,代表的是平均值,详情请看MarkType对象
			bar2.markLine().data(new PointData().type(MarkType.average).name("平均值"));
	
			option.series(bar, bar2);

		}else {
			option.setNoDataLoadingOption(new NoDataLoadingOption().text("无数据"));
		}
		
		//设置间隔
		option.grid(new Grid().y2(180));
		getResponse().setContentType("text/html;charset=utf-8");
		getResponse().getWriter().write(option.toString());

	}

	/**
	 * Ajax按月份进行统计交易额.
	 * @throws IOException 
	 */
	public void MonthBar() throws IOException {

		// 初始化
		List<Object> xAxisList = new ArrayList<>();
		List<Object> barList = new ArrayList<>();

		// 创建数据承载对象
		GsonOption option = new GsonOption();
		
		List<Map<String,Object>> list = yybusinessService.findAllByMoth();
		
		if (list!=null) {
			
			for (Map<String, Object> map : list) {
				xAxisList.add(map.get("YF")+"月");
				BigDecimal object = (BigDecimal) map.get("CGJE");
				barList.add(object.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			
			
			// ******设置基本属性*******start
			// 设置标题和子标题(子标题即主标题下的小文字说明)
			option.title().text("医药采购信息-按月份统计").subtext("一组");
			// 设置鼠标移入数据柱中提示框,可自行设置
			option.tooltip().trigger(Trigger.axis);
			// 添加图标图例,每个图标仅可有一个图例(就是统计图上方的统计说明)
			option.legend("采购金额");
	
			option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true),
					Tool.restore, Tool.saveAsImage);
			// 设置calculable值,该值是什么?不知道
			option.calculable(true);
	
			option.xAxis(new CategoryAxis().data(xAxisList.toArray()));
			// yAxis:即y轴需要显示的数据,简单来说就是数据的数值,如果直接new ValueAxis()
			// 应该是根据数据的需求进行自动显示
			option.yAxis(new ValueAxis());
	
			Bar bar = new Bar("采购金额");
			// data:就是该bar的数据,需要和上面的城镇名称进行对应
	
			bar.data(barList.toArray());
			// 设置标注,markPoint是一个标注对象,支持链式编程,可以直接设置值,很多特效可以在这里设置
			bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"),
					new PointData().type(MarkType.min).name("最小值"));
			// 设置标线,也就是横穿数据柱的一条线,代表的是平均值,详情请看MarkType对象
			bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));
	
			option.series(bar);
	
			}else {
				option.setNoDataLoadingOption(new NoDataLoadingOption().text("无数据"));
			}
			getResponse().setContentType("text/html;charset=utf-8");
			getResponse().getWriter().write(option.toString());
	
	}

}
