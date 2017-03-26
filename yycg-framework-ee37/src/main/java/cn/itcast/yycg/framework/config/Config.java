package cn.itcast.yycg.framework.config;


/**
 * 系统参数配置类，配置了系统用到参数
 * @author liangtong
 * 
 */
public class Config {
	
	/**
	 * 系统模块如下：
	 * 
	 * ypml：药品目录
	 * cgd：采购单
	 * thd：退货单
	 * perm：权限管理
	 * analyze：统计分析 (analyze gather 统计聚合)
	 * user：用户管理
	 * sytem：系统管理
	 * first：首页
	 * auth：认证授权
	 */


	/**
	 * 系统语言环境，默认为中文zh
	 */
	public static final String LANGUAGE = "zh";

	/**
	 * 系统国家环境，默认为中国CN
	 */
	public static final String COUNTRY = "CN";
	
	
	
	
	/**
	 * 系统参数配置文件名称
	 */
	public static final String CONFIG_DB = "config.db";
	
	
	/**
	 * 系统版本文件名称
	 */
	public static final String VERSION = "version";
	
	
	/**
	 * session中存放登录用户的key名称
	 */
	public static final String ACTIVEUSER_KEY = "activeUser";
	
	/**
	 * 配置文件中系统基础url的key名称
	 */
	public static final String BASEURL_KEY = "baseurl";
	
	
	/**
	 * 配置文件中系统版本名称的key名称
	 */
	public static final String VERSION_NAME_KEY = "version_name";
	
	/**
	 * 配置文件中系统版本号的key名称
	 */
	public static final String VERSION_NUMBER_KEY = "version_number";
	
	/**
	 * 配置文件中系统版本发布时间的key名称
	 */
	public static final String VERSION_DATE_KEY = "version_date";
	
	/**
	 * 系统提示信息ResultInfo对象key
	 */
	public static final String RESULTINFO_KEY = "resultInfo";
	
		
	/**
	 * 基础模块存放页面路径 ，在WebRoot下
	 */
	public static final String PAGE_PATH_BASE = "/common";
	
	/**
	 * 业务模块存放页面路径 ，在/WEB-INF/jsp下
	 */
	public static final String PAGE_PATH_BUSINESS = "/business";
	
		

	/**
	 * 一般错误提示页面,该路径需要匹配页面前后缀
	 */
	public static final String ERROR_PAGE = "/common/error";
	/**
	 * 登录页面地址,该路径需要匹配页面前后缀
	 */
	public static final String LOGIN_PAGE = "/common/login";
	
	/**
	 * 无权提示页面地址,该路径需要匹配页面前后缀
	 */
	public static final String REFUSE_PAGE = "/common/refuse";
	

	
}
