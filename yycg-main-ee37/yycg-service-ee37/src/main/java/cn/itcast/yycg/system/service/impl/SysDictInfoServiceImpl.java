package cn.itcast.yycg.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.SysDictType;
import cn.itcast.yycg.system.dao.SysDictInfoDao;
import cn.itcast.yycg.system.service.SysDictInfoService;

@Service
public class SysDictInfoServiceImpl implements SysDictInfoService {

	@Autowired
	private SysDictInfoDao sysDictInfoDao;
	@Override
	public List<SysDictInfo> findAll(String typecode) {
		//封装数据
		//创建字典类别对象，保存字典类别码typecode(id)
		SysDictType sysDictType = new SysDictType();
		sysDictType.setTypecode(typecode);
		//创建字典信息对象，保存字典类别对象
		SysDictInfo sysDictInfo = new SysDictInfo();
		sysDictInfo.setSysDictType(sysDictType);
		//传递参数，查询到字典信息
		return sysDictInfoDao.findAll(sysDictInfo);
	}
	@Override
	public SysDictInfo findById(String id) {
		return sysDictInfoDao.findById(id);
	}

}
