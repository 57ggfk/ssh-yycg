package cn.itcast.yycg.province.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.province.dao.YpxxShengDao;
import cn.itcast.yycg.province.po.YpxxSheng;
import cn.itcast.yycg.province.service.YpxxShengService;

@Service("ypxxShengService")
public class YpxxShengServiceImpl implements YpxxShengService {

	@Autowired
	private YpxxShengDao ypxxShengDao;
	
	@Override
	public List<YpxxSheng> findAll() {
		return ypxxShengDao.findAll();
	}

}
