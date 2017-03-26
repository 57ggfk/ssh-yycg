package cn.itcast.yycg.system.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysConf;

public interface SysConfService {
	public Integer  findCount(SysConf sysConf);
	public List<SysConf> findAll(SysConf sysConf,Integer firstResult, Integer maxReslults);
	/**
	 * 更新系统设置
	 * @param sysid
	 * @param sysConfId
	 * @param sysConfValue
	 */
	public void updateForValue(String sysid, String[] sysConfId, String[] sysConfValue);
	public SysConf findById(String id);
	/**
	 * 将sysConf的值和id到service层进行封装
	 * @param sysConfId
	 * @param value
	 */
	public void update(String sysConfId, String value);
	
	/**
	 * 获取文件上传要保存的路径
	 * @return
	 */
	public String getUploadPath();
}
