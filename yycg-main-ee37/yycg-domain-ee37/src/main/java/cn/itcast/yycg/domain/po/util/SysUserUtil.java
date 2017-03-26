package cn.itcast.yycg.domain.po.util;

import org.apache.log4j.Logger;

import cn.itcast.yycg.domain.po.SysUser;

public class SysUserUtil {
	
	private static Logger LOG = Logger.getLogger(SysUserUtil.class);
	
	/**
	 * 用户类型/卫生局
	 */
	public static String USER_GROUP_DICTCODE_WSJ = "1";
	/**
	 * 用户类型/卫生院
	 */
	public static String USER_GROUP_DICTCODE_WSY = "2";
	/**
	 * 用户类型/卫生室
	 */
	public static String USER_GROUP_DICTCODE_WSS = "3";
	/**
	 * 用户类型/供应商
	 */
	public static String USER_GROUP_DICTCODE_GYS= "4";
	
	
	/**
	 * 获得单位名称。
	 * @param sysUser
	 * @return 如果数据位空，将返回null。
	 */
	public static String getDW_MC(SysUser sysUser){
		if(sysUser == null){
			LOG.info("SysUserUtil.getDW_MC，获得“单位名称”时，参数为空。");
			return null;
		}
		
		if(sysUser.getSysDictInfoByGroupid() == null){
			LOG.info("SysUserUtil.getDW_MC，获得“单位名称”时，用户类别为空。");
			return null;
		}
		
		// 处理：卫生局和卫生院
		if(USER_GROUP_DICTCODE_WSJ.equals(sysUser.getSysDictInfoByGroupid().getDictcode())
				|| USER_GROUP_DICTCODE_WSY.equals(sysUser.getSysDictInfoByGroupid().getDictcode())){
			if(sysUser.getDwWsy() == null){
				if(sysUser.getDwWsy() == null){
					LOG.info("SysUserUtil.getDW_MC，获得“单位名称”时，卫生院为空。");
					return null;
				}
			}
			return sysUser.getDwWsy().getMc();
		}
		
		// 处理：卫生室
		if(USER_GROUP_DICTCODE_WSS.equals(sysUser.getSysDictInfoByGroupid().getDictcode())){
			if(sysUser.getDwWss() == null){
				if(sysUser.getDwWss() == null){
					LOG.info("SysUserUtil.getDW_MC，获得“单位名称”时，卫生室为空。");
					return null;
				}
			}
			return sysUser.getDwWss().getMc();
		}
		
		// 处理：供应商
		if(USER_GROUP_DICTCODE_GYS.equals(sysUser.getSysDictInfoByGroupid().getDictcode())){
			if(sysUser.getDwGys() == null){
				if(sysUser.getDwGys() == null){
					LOG.info("SysUserUtil.getDW_MC，获得“单位名称”时，供应商为空。");
					return null;
				}
			}
			return sysUser.getDwGys().getMc();
		}
		
		LOG.info("SysUserUtil.getDW_MC，获得“单位名称”没有对应数据。");
		return null;
	}
	
	/**
	 * 获得单位id。
	 * @param sysUser
	 * @return 如果数据位空，将返回null。
	 */
	public static String getDW_Id(SysUser sysUser){
		if(sysUser == null){
			LOG.info("SysUserUtil.getDW_Id，获得“单位Id”时，参数为空。");
			return null;
		}
		
		if(sysUser.getSysDictInfoByGroupid() == null){
			LOG.info("SysUserUtil.getDW_Id，获得“单位Id”时，用户类别为空。");
			return null;
		}
		
		// 处理：卫生局和卫生院
		if(USER_GROUP_DICTCODE_WSJ.equals(sysUser.getSysDictInfoByGroupid().getDictcode())
				|| USER_GROUP_DICTCODE_WSY.equals(sysUser.getSysDictInfoByGroupid().getDictcode())){
			if(sysUser.getDwWsy() == null ){
				LOG.info("SysUserUtil.getDW_Id，获得“单位Id”时，卫生院为空。");
				return null;
			}
			return sysUser.getDwWsy().getId();
		}
		
		// 处理：卫生室
		if(USER_GROUP_DICTCODE_WSS.equals(sysUser.getSysDictInfoByGroupid().getDictcode())){
			if(sysUser.getDwWss() == null){
				LOG.info("SysUserUtil.getDW_Id，获得“单位Id”时，卫生室为空。");
				return null;
			}
			return sysUser.getDwWss().getId();
		}
		
		// 处理：供应商
		if(USER_GROUP_DICTCODE_GYS.equals(sysUser.getSysDictInfoByGroupid().getDictcode())){
			if(sysUser.getDwGys() == null){
				LOG.info("SysUserUtil.getDW_Id，获得“单位Id”时，供应商为空。");
				return null;
			}
			return sysUser.getDwGys().getId();
		}
		
		LOG.info("SysUserUtil.getDW_Id，获得“单位Id”没有对应数据。");
		return null;
	}
	
	
	

}
