package cn.itcast.yycg.analyze.service;

import java.util.List;

import cn.itcast.yycg.domain.po.SysTask;

public interface SysTaskService {
	
	public List<SysTask> findSysTaskTopList(int num);
	
}
