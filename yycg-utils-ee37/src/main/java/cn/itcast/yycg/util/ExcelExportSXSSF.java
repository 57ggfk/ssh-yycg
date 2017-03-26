package cn.itcast.yycg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * excel导出的封装类
 * @author lt
 *
 */
public class ExcelExportSXSSF {

	// 定义工作表
	private SXSSFWorkbook wb;

	/**
	 * 定义工作表中的sheet
	 */
	private Sheet sh;


	/**
	 * 定义保存在内存中的数量,-1表示手动控制
	 */
	private int flushRows;
	/** 导出文件行数 */
	private int rownum;
	/** 导出文件列数 */
	private int colnum;

	/** 导出文件的存放路径 */
	private String filePath;
	/** 下载导出文件的路径 */
	private String fileWebPath;
	/**文件名称前缀*/
	private String filePrefix;
	/**导出文件全路径*/
	private String fileAllPath;
	/** 导出文件列标题 */
	private List<String> fieldNames;
	/**导出文件每列代码，用于反射获取对象属性值*/
	private List<String> fieldCodes;

	private ExcelExportSXSSF() {

	}

	/**
	 * 开始导出方法
	 * 
	 * @param filePath
	 *            导出文件存放物理路径
	 * @param fileWebPath
	 *            导出文件web下载路径
	 * @param filePrefix
	 *            导出文件名的前缀          
	 * @param flushRows
	 *            存放在内存的数据量
	 * @param fieldNames
	 *            导出文件列标题
	 * @param fieldCodes
	 * 			  导出数据对象的字段名称     
	 * @param flushRows
	 * 			写磁盘控制参数
	 * @return
	 */
	public static ExcelExportSXSSF start(String filePath, String fileWebPath,String filePrefix,
			List<String> fieldNames,List<String> fieldCodes, int flushRows) throws Exception {
		ExcelExportSXSSF ExcelExportSXSSF = new ExcelExportSXSSF();
		ExcelExportSXSSF.setFilePath(filePath);
		ExcelExportSXSSF.setFileWebPath(fileWebPath);
		ExcelExportSXSSF.setFilePrefix(filePrefix);
		ExcelExportSXSSF.setFieldNames(fieldNames);
		ExcelExportSXSSF.setFieldCodes(fieldCodes);
		ExcelExportSXSSF.setWb(new SXSSFWorkbook(flushRows));//创建workbook
		ExcelExportSXSSF.setSh(ExcelExportSXSSF.getWb().createSheet());//创建sheet
		ExcelExportSXSSF.writeTitles();
		return ExcelExportSXSSF;
	}

	/**
	 * 开始导出方法
	 * 
	 * @param filePath
	 *            导出文件存放物理路径
	 * @param fileWebPath
	 *            导出文件web下载路径
	 * @param filePrefix
	 *            导出文件名的前缀          
	 * @param flushRows
	 *            存放在内存的数据量
	 * @param fields
	 *            key导出文件列标题 value导出数据对象的字段名称     
	 * @param flushRows
	 * 			写磁盘控制参数
	 * @return
	 */
	public static ExcelExportSXSSF start(String filePath, String fileWebPath,String filePrefix,
			LinkedHashMap<String, String> fields, int flushRows) throws Exception {
		
		//把Map拆成List
		List<String> fieldCodes = new ArrayList<String>();		//取key的值
		List<String> fieldNames = new ArrayList<String>();		//取value的值
		for (Map.Entry<String, String> field: fields.entrySet()) {
			fieldCodes.add(field.getKey());
			fieldNames.add(field.getValue());
		}
		return start(filePath, fileWebPath, filePrefix, fieldNames, fieldCodes, flushRows);
	}
	
	/**
	 * 设置导入文件的标题
	 * 开始生成导出excel的标题
	 * @throws Exception
	 */
	private void writeTitles() throws Exception {
		rownum = 0;//第0行
		colnum = fieldNames.size();//根据列标题得出列数
		Row row = sh.createRow(rownum);
		for (int cellnum = 0; cellnum < colnum; cellnum++) {
			Cell cell = row.createCell(cellnum);
			cell.setCellValue(fieldNames.get(cellnum));
		}
	}

	/**
	 * 向导出文件写数据
	 * 
	 * @param datalist
	 *            存放Object对象，仅支持单个自定义对象，不支持对象中嵌套自定义对象
	 * @return
	 */
	public <T> void  writeDatasByObject(List<T> datalist) throws Exception {

		for (int j = 0; j < datalist.size(); j++) {
			rownum = rownum + 1;
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < fieldCodes.size(); cellnum++) {
				String fieldCode = fieldCodes.get(cellnum);
				Object owner = null;
				Object value = null;
				if(fieldCode.indexOf(".")<0){
					owner = datalist.get(j);
					value = invokeMethod(owner, fieldCodes.get(cellnum),
							new Object[] {});
					
				}else{
					owner = datalist.get(j);
					String[] fieldCodeArray = fieldCode.split("\\.");
					for(int i=0;i<fieldCodeArray.length-1;i++){
						owner = invokeMethod(owner, fieldCodeArray[i],
								new Object[] {});
					}
					value = invokeMethod(owner, fieldCodeArray[fieldCodeArray.length-1],
							new Object[] {});
				}
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(value!=null?value.toString():"");
			}

		}

	}
	/**
	 * 向导出文件写数据
	 * 
	 * @param datalist
	 *            存放字符串数组
	 * @return
	 */
	public void writeDatasByString(List<String> datalist) throws Exception {
			rownum = rownum + 1;
			Row row = sh.createRow(rownum);
			int datalist_size = datalist.size();
			for (int cellnum = 0; cellnum < colnum; cellnum++) {
				Cell cell = row.createCell(cellnum);
				if(datalist_size>cellnum){
					cell.setCellValue(datalist.get(cellnum));
				}else{
					cell.setCellValue("");
				}
				
			}
	}

	/**
	 * 手动刷新方法,如果flushRows为-1则需要使用此方法手动刷新内存
	 * 
	 * @param flushRows
	 * @throws Exception
	 */
	public void flush(int flushNum) throws Exception {
		((SXSSFSheet) sh).flushRows(flushNum);
	}

	/**
	 * 导出文件
	 * 
	 * @throws Exception
	 */
	public String exportFile() throws Exception {
		String filename = filePrefix+"_"+MyUtil.getCurrentTimeStr() + ".xlsx";
		FileOutputStream out = new FileOutputStream(new File( filePath,filename ));
		wb.write(out);
		out.flush();
		out.close();
		setFileAllPath(fileWebPath + filename);
		return fileWebPath + filename;
	}

	/**
	 * 反射方法，通过get方法获取对象属性
	 * 
	 * @param owner
	 * @param fieldname
	 * @param args
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object invokeMethod(Object owner, String fieldname, Object[] args)
			throws Exception {
		//非空判断
		if(owner == null){
			return "";
		}

		String methodName = "get" + fieldname.substring(0, 1).toUpperCase()
				+ fieldname.substring(1);
		Class ownerClass = owner.getClass();

		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	public SXSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(SXSSFWorkbook wb) {
		this.wb = wb;
	}

	public Sheet getSh() {
		return sh;
	}

	public void setSh(Sheet sh) {
		this.sh = sh;
	}


	public int getFlushRows() {
		return flushRows;
	}

	public void setFlushRows(int flushRows) {
		this.flushRows = flushRows;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileWebPath() {
		return fileWebPath;
	}

	public void setFileWebPath(String fileWebPath) {
		this.fileWebPath = fileWebPath;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public List<String> getFieldCodes() {
		return fieldCodes;
	}

	public void setFieldCodes(List<String> fieldCodes) {
		this.fieldCodes = fieldCodes;
	}

	public int getRownum() {
		return rownum;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public int getColnum() {
		return colnum;
	}

	public String getFileAllPath() {
		return fileAllPath;
	}

	public void setFileAllPath(String fileAllPath) {
		this.fileAllPath = fileAllPath;
	}
	
	public static void main(String[] args) throws Exception {
		/**            导出文件存放物理路径
		 * @param fileWebPath
		 *            导出文件web下载路径
		 * @param filePrefix
		 *            导出文件名的前缀          
		 * @param flushRows
		 *            存放在内存的数据量
		 * @param fieldNames
		 *            导出文件列标题
		 * @param fieldCodes
		 * 			  导出数据对象的字段名称     
		 * @param flushRows*/
		//导出文件存放的路径，并且是虚拟目录指向的路径
		String filePath = "f:/develop/upload/temp/";
		//导出文件的前缀
		String filePrefix="ypxx";
		//-1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
		int flushRows=100;
		
		//指导导出数据的title
		List<String> fieldNames=new ArrayList<String>();
		fieldNames.add("流水号");
		fieldNames.add("通用名");
		fieldNames.add("价格");
		
		//告诉导出类数据list中对象的属性，让ExcelExportSXSSF通过反射获取对象的值
		List<String> fieldCodes=new ArrayList<String>();
		fieldCodes.add("bm");//药品流水号
		fieldCodes.add("mc");//通用名
		fieldCodes.add("price");//价格
		
		
		
		//注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
		
		
		//开始导出，执行一些workbook及sheet等对象的初始创建
		ExcelExportSXSSF excelExportSXSSF = ExcelExportSXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
		
		//准备导出的数据，将数据存入list，且list中对象的字段名称必须是刚才传入ExcelExportSXSSF的名称
		List<TestYpxx> list = new ArrayList<TestYpxx>();
		
		TestYpxx ypxx1 = new TestYpxx("001", "青霉素", 5);
		TestYpxx ypxx2 = new TestYpxx("002", "感冒胶囊", 2.5f);
		list.add(ypxx1);
		list.add(ypxx2);
		
		//执行导出
		excelExportSXSSF.writeDatasByObject(list);
		//输出文件，返回下载文件的http地址
		String webpath = excelExportSXSSF.exportFile();
		
		System.out.println(webpath);
		
		
	}

}
