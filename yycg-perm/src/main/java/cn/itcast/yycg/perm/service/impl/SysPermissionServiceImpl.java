package cn.itcast.yycg.perm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.perm.dao.SysPermissionDao;
import cn.itcast.yycg.perm.dao.SysRoleDao;
import cn.itcast.yycg.perm.pojo.SysPermQueryCustom;
import cn.itcast.yycg.perm.pojo.SysPermissionCustom;
import cn.itcast.yycg.perm.service.SysPermissionService;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Autowired
	private SysPermissionDao sysPermissionDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	
	@Override
	public SysPermission findById(String id) {
		return sysPermissionDao.findById(id);
	}
	
	@Override
	public List<SysPermission> findAll() {
		return sysPermissionDao.findAll();
	}

	@Override
	public List<SysPermission> findAllByRoleId(String roleId) {
		
		SysRole sysRole = sysRoleDao.findById(roleId);
		
		Set<SysPermission> sysPermissions = sysRole.getSysPermissions();
		
		List<SysPermission> list = new ArrayList<>();
		list.addAll(sysPermissions);
		return list;
	}

	@Override
	public void updatePermission(String roleId, List<String> addPermIds) {
		SysRole sysRole = sysRoleDao.findById(roleId);
		
		Set<SysPermission> sysPermissions = sysRole.getSysPermissions();
		
		//移除之前的所有
		sysPermissions.clear();
		
		//添加
		boolean unchange = true;
		if(addPermIds != null){
			//添加
			for (String addPermId : addPermIds) {
				SysPermission addPerm = sysPermissionDao.findById(addPermId);
				//不存在添加，存在了及忽略(提交前点击两次)
				if(! permExist(sysPermissions, addPermId) ){
					sysPermissions.add(addPerm);
					unchange = false;
				}
			}
		}
		
		
		if(unchange){
			ResultUtil.throwExcepionResult("权限没有更改");
		}
				
	}
	
	private boolean permExist(Set<SysPermission> permSet , String permId){
		for (SysPermission sysPerm : permSet) {
			if(sysPerm.getId().equals(permId)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int findChildCount(String parentId) {
		return sysPermissionDao.findChildCount(parentId);
	}

	@Override
	public void savePermission(SysPermissionCustom sysPermissionCustom) {
		SysPermission sysPermission=new SysPermission();
		BeanUtils.copyProperties(sysPermissionCustom, sysPermission);
		sysPermissionDao.save(sysPermission);
		
	}

	@Override
	public void update(SysPermissionCustom sysPermissionCustom) {
		String id=sysPermissionCustom.getId();
		SysPermission sysPermission=sysPermissionDao.findById(id);
		BeanUtils.copyProperties(sysPermissionCustom, sysPermission);
		sysPermissionDao.update(sysPermission);
	}

	@Override
	public void delete(SysPermission sysPermission) {
		sysPermissionDao.delete(sysPermission);
	}

	@Override
	public int findCount(SysPermQueryCustom sysPermQueryCustom) {
		return sysPermissionDao.findCount(sysPermQueryCustom);
	}

	@Override
	public List<SysPermission> findAllList(SysPermQueryCustom sysPermQueryCustom, int firstResult, int maxsResult) {
		return sysPermissionDao.findAllList(sysPermQueryCustom,firstResult,maxsResult);
	}

	@Override
	public void updateAll(SysPermission sysPermission) {
		sysPermissionDao.update(sysPermission);
	}

	@Override
	//删除用户权限
	public void deleteById(String id) {
		//先查询,看看是否为空
		SysPermission sysPermission = sysPermissionDao.findById(id);
		if(sysPermission==null){
			ResultUtil.throwExcepionResult("删除失败!");
		}
		sysPermissionDao.delete(sysPermission);
	}

	@Override
	//添加权限数据
	public void addData(String[] allData) {
		//将数组中的数据校验
			//首先判断id是否为空
		if(allData[0].trim().equals("A")){
			ResultUtil.throwExcepionResult("权限id不能为空");
		}
			//首先根据id查询权限表
		String id = allData[0].substring(0, allData[0].length()-1).trim();
		//根据id查询表
		SysPermission sysPermission = sysPermissionDao.findById(id);
		if(sysPermission!=null){
			ResultUtil.throwExcepionResult("权限id已经存在,请重新修改");
		}
		sysPermission = new SysPermission();
		//封装数据
		sysPermission.setId(id);
		//获得name值
		String names= allData[1].trim();
		if("A".equals(names)){
			ResultUtil.throwExcepionResult("模块名称不能为空");
		}
		if(!"A".equals(names)){
			String name = names.substring(0, names.length()-1);
			sysPermission.setName(name);
		}
		//根据id获取父id
		String parentIds = id.substring(id.length()-1, id.length());
		if(".".equals(parentIds)){
			String parentId = id.substring(0,id.length()-3);
			sysPermission.setParentid(parentId);
		}else{
			String parentId = id.substring(0,id.length()-2);
			sysPermission.setParentid(parentId);
		}
		//url
		String urls =  allData[2].trim();
		if(!"A".equals(urls)){
			String url = urls.substring(0, urls.length()-1);
			sysPermission.setUrl(url);
		}
		//模块图标
		String icons = allData[3].trim();
		if(!"A".equals(icons)){
			String icon = icons.substring(0, icons.length()-1);
			sysPermission.setIcon(icon);
		}
		//显示顺序
		String showorders = allData[4].trim();
		if(!"A".equals(showorders)){
			String showorder = showorders.substring(0, showorders.length()-1);
			sysPermission.setShoworder(showorder);
		}
		//状态标记
		String isuseds = allData[5].trim();
		if(!"A".equals(isuseds)){
			String isused = isuseds.substring(0, isuseds.length()-1);
			if(!("0".equals(isused) || "1".equals(isused))){
				ResultUtil.throwExcepionResult("填写的状态标记不符合要求");
			}
			sysPermission.setIsused(isused);
		}
		//状态标记菜单
		String ismenus = allData[6].trim();
		if(!"A".equals(ismenus)){
			String ismenu = ismenus.substring(0, ismenus.length()-1);
			if(!("0".equals(ismenu) || "1".equals(ismenu))){
				ResultUtil.throwExcepionResult("填写的状态标记菜单"+ismenu+"不符合要求");
			}
			sysPermission.setIsmenu(ismenu);
		}
		//层级
		String plevels = allData[7].trim();
		if(!"A".equals(plevels)){
			String plevel = plevels.substring(0, plevels.length()-1);
			if(plevel.length()>=2){
				ResultUtil.throwExcepionResult("填写的层级[  "+plevel+"  ]不符合要求");
			}
			sysPermission.setPlevel(plevel);
		}
		//权限标识代码
		String pcodes = allData[8].trim();
		if(!"A".equals(pcodes)){
			String pcode = pcodes.substring(0, pcodes.length()-1);
			sysPermission.setPcode(pcode);
		}
		//权限标识代码
		String vchar1s = allData[9].trim();
		if(!"A".equals(vchar1s)){
			String vchar1 = vchar1s.substring(0, vchar1s.length()-1);
			sysPermission.setVchar1(vchar1);
		}
		//2.0保存数据
		sysPermissionDao.save(sysPermission);
	}

}
