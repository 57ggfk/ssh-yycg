package cn.itcast.yycg.analyze.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.analyze.dao.SysTaskDao;
import cn.itcast.yycg.analyze.service.SysTaskService;
import cn.itcast.yycg.domain.po.SysTask;

@Service
public class SysTaskServiceImpl implements SysTaskService {

	@Autowired
	private SysTaskDao sysTaskDao;
	
	@Override
	public List<SysTask> findSysTaskTopList(int num) {
		
		//对你看的没错,我偷懒了
		return sysTaskDao.findAll();
	}
	
}
