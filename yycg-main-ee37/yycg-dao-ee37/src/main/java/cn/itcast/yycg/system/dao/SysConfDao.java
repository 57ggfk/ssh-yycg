package cn.itcast.yycg.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.SysConf;
public interface SysConfDao extends BaseDao<SysConf>{
	public Integer findCount(SysConf sysConf);
	public List<SysConf> findAll(SysConf sysConf,Integer firstResult , Integer maxResults);
}
