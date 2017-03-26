package cn.itcast.yycg.system.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.DwGys;
import cn.itcast.yycg.domain.po.DwWss;
import cn.itcast.yycg.domain.po.DwWsy;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.system.dao.DwGysDao;
import cn.itcast.yycg.system.dao.DwWssDao;
import cn.itcast.yycg.system.dao.DwWsyDao;
import cn.itcast.yycg.system.service.DwService;
@Service
public class DwServiceImpl implements DwService {

	@Autowired
	private DwWsyDao dwWsyDao;
	@Autowired
	private DwWssDao dwWssDao;
	@Autowired
	private DwGysDao dwGysDao;
	
	public List<?> findDwByDictcode(String dictcode){
		if ("1".equals(dictcode)||"2".equals(dictcode)) {
			return dwWsyDao.findAll();
			
		} else if ("3".equals(dictcode)) {
			return dwWssDao.findAll();
		} else if ("4".equals(dictcode)) {
			return dwGysDao.findAll();
		}
		return null;
	}
	
	
	
	
	

	@Override
	//根据sysid判断用户是否是wsy
	public void findAllByWsy(String sysid) {
		DwGys dwGys = dwGysDao.findById(sysid);
		if(dwGys!=null){
			ResultUtil.throwExcepionResult("小样,想干嘛,这不是你能动的东西!");
		}
		DwWss dwWss = dwWssDao.findById(sysid);
		if(dwWss!=null){
			ResultUtil.throwExcepionResult("小样,想干嘛,别乱修改东西!");
		}
	}
}
