package cn.itcast.yycg.perm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.perm.dao.SysRoleDao;
import cn.itcast.yycg.perm.pojo.SysRoleQueryCustom;
import cn.itcast.yycg.perm.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleDao sysRoleDao;

	@Override
	public int getTotalRecord(SysRoleQueryCustom sysRoleQueryCustom) {
		return sysRoleDao.getTotalRecord(sysRoleQueryCustom);
	}

	@Override
	public List<SysRole> findAll(SysRoleQueryCustom sysRoleQueryCustom, int firstResult, int maxResults) {
		return sysRoleDao.findAll(sysRoleQueryCustom,firstResult ,maxResults );
	}

	@Override
	public SysRole findById(String id) {
		return sysRoleDao.findById(id);
	}
	
	

}
