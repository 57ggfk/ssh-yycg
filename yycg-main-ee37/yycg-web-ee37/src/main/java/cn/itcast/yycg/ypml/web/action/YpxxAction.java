package cn.itcast.yycg.ypml.web.action;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.province.service.YpxxSheng;
import cn.itcast.yycg.province.service.YpxxShengService;
import cn.itcast.yycg.system.service.SysConfService;
import cn.itcast.yycg.system.service.SysDictInfoService;
import cn.itcast.yycg.util.ExcelExportSXSSF;
import cn.itcast.yycg.util.HxlsOptRowsInterface;
import cn.itcast.yycg.util.HxlsRead;
import cn.itcast.yycg.util.MyUtil;
import cn.itcast.yycg.util.PageBean;
import cn.itcast.yycg.ypml.pojo.YpxxQueryCustom;
import cn.itcast.yycg.ypml.service.YpxxService;
import cn.itcast.yycg.ypml.web.vo.YpxxVo;

@Controller
@Scope("prototype")
public class YpxxAction extends BaseAction<YpxxVo> {

	@Autowired
	private YpxxService ypxxService;
	@Autowired
	private SysDictInfoService sysDictInfoService;
	@Autowired
	private HxlsOptRowsInterface hxlsOptRows;
	private YpxxVo ypxxVo = getModel();
	@Autowired
	private SysConfService sysConfService;
	@Autowired
	private YpxxShengService ypxxShengService;
	
	//jsp页面展示用于导出Excel
	public String exportypxx() {
		//准备药品类别列表001
		List<SysDictInfo> yplbList  = sysDictInfoService.findAll("001");
		ypxxVo.setYplbList(yplbList);
		//准备药品交易状态列表003
		List<SysDictInfo> ypjyztList = sysDictInfoService.findAll("003");
		ypxxVo.setYpjyztList(ypjyztList);
		return "exportypxx";
	}
	
	//导出功能
	public void exportypxx_submit() throws Exception {
		
		//准备参数
		YpxxQueryCustom ypxxQueryCustom = ypxxVo.getYpxxQueryCustom();
		//查询数据
		List<Ypxx> allYpxx = ypxxService.findAll(ypxxQueryCustom);
		//导出到表格
		LinkedHashMap<String,String> map = new LinkedHashMap<>();
		map.put("bm", "流水号");
		map.put("scqymc", "生产企业名称");
		map.put("spmc", "商品名");
		map.put("mc", "通用名");
		map.put("jx", "剂型");
		map.put("gg", "规格");
		map.put("zhxs", "转换系数");
		map.put("dw", "单位");
		map.put("zbjg", "中标价格");
		map.put("sysDictInfoByJyzt.info", "交易状态");
		map.put("sysDictInfoByLb.info", "药品类别");
		
		//获取路径
		String uploadPath = sysConfService.getUploadPath();
		ExcelExportSXSSF exportSXSSF = ExcelExportSXSSF.start(uploadPath, "/download/", "ypxx", map, 100);
		exportSXSSF.writeDatasByObject(allYpxx);
		
		String exportFile = exportSXSSF.exportFile();
		
		this.write_object(ResultUtil.createSubmitResult("导出"+allYpxx.size()+"条，<a href='"+exportFile+"' target='_black'>点击下载</a>"));
		
	}
	
	//导入Excel药品目录页面回显
	public String importypxx() {
		return "importypxx";
	}
	
	public void importypxx_submit() throws Exception {
		
		//获取文件流
		File upfile = ypxxVo.getYpxximportfile();
		//保存到服务器备份
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String usercode = activeUser==null?"":activeUser.getUsercode();
		
		//获取路径
		String uploadPath = sysConfService.getUploadPath();
				
		File file = new File(uploadPath,usercode+MyUtil.yymmdddateToString(new Date())+".xls");
		FileCopyUtils.copy(upfile, file);
		//解析，使用工具类 hxlsOptRows在Service层实现类YpxxImportServiceImpl 
		HxlsRead hxlsRead = new HxlsRead(file.getAbsolutePath(),1,hxlsOptRows);
		
		hxlsRead.process();
		
		//标红Excel错误信息
		String downMsg = createErrorExcel(file,hxlsRead.getFailrows(),hxlsRead.getFailmsgs());
		
		//处理结果信息
		String msg = "共上传x条，成功y条，失败z条";
//		String msg = "共上传<font color='blue'>"+hxlsRead.getOptRows_sum()+
//				"</font>条，成功<font color='green'>"+hxlsRead.getOptRows_success()+
//				"</font>条，失败<font color='red'>"+hxlsRead.getOptRows_failure()+"</font>条";
		
//		msg.replace("x", ""+hxlsRead.getOptRows_sum());
//		msg.replace("y", ""+hxlsRead.getOptRows_success());
//		msg.replace("z", ""+hxlsRead.getOptRows_failure());
//		msg.replace("x", Long.valueOf(hxlsRead.getOptRows_sum()).toString());
//		msg.replace("y", Long.toString(hxlsRead.getOptRows_success()));
//		msg.replace("z", Long.toString(hxlsRead.getOptRows_failure()));
		
		msg = msg.replace("x", "<font color='blue'>"+hxlsRead.getOptRows_sum()+"</font>");
		msg = msg.replace("y", "<font color='green'>"+hxlsRead.getOptRows_success()+"</font>");
		msg = msg.replace("z", "<font color='red'>"+hxlsRead.getOptRows_failure()+"</font>");
		msg = msg+downMsg;
		this.write_object(ResultUtil.createSubmitResult(msg, hxlsRead.getFailmsgs()));
	}
	
	//成功失败提示Excel
	
	
	//药品信息同步页面
	public String syncypxx() {
		return "syncypxx";
	}
	
	//药品信息同步提交
	public void syncypxx_submit() {
		//获得省级平台数据
		List<YpxxSheng> allYpxxSheng = ypxxShengService.findAll();
		
		//保存到数据库，返回成功条数
		int successNumber = ypxxService.saveSyncypxx(allYpxxSheng);
		
		//返回提示信息
		String message = "同步{x}条，成功{y}条";
		message = message.replace("{x}", allYpxxSheng.size()+"").replace("{y}", successNumber+"");
		write_object(ResultUtil.createSubmitResult(message));
		
	}
	
	//标红错误信息到Excel
	private String createErrorExcel(File filePath, List<List<String>> errorRows,List<String> errorMsgs) {
		if (errorRows==null | errorRows.size()<1) {
			return "";				//表示没有错误
		}
		if (errorMsgs==null || errorMsgs.size()<1) {
			return "";				//表示没有错误
		}
		if (filePath==null || !filePath.exists()) {
			return "";				//文件丢失
		}
		
		HSSFWorkbook hssworkbook = new HSSFWorkbook();
		
		//遍历
		
		return "";
		
	}
	
	/**
	 * 查询药品信息，条件+分页
	 */
	public void list_result() {
		//准备参数
		YpxxQueryCustom ypxxQueryCustom = ypxxVo.getYpxxQueryCustom();
		
		int total = ypxxService.getTotalRecord(ypxxQueryCustom);
		PageBean pageBean = new PageBean(ypxxVo.getPage(),ypxxVo.getRows(),total);
		List<Ypxx> rows = ypxxService.findAll(ypxxQueryCustom,pageBean.getStartIndex(),pageBean.getPageSize());
		
		write_object(ResultUtil.createDataGridResult(total, rows));
	}
}
