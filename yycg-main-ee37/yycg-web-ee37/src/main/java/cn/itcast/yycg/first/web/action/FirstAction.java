package cn.itcast.yycg.first.web.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.first.web.vo.MenuVo;
import cn.itcast.yycg.user.service.SysUserService;

@Controller
@Scope("prototype")
public class FirstAction extends BaseAction<MenuVo>{

	@Autowired
	private SysUserService sysUserService;
	
	public String first(){
		//获取认证信息
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		//存入值栈，用于在页面中取到用户名称
		ActionContext.getContext().getValueStack().set("activeUser", activeUser);
		return "first";
	}
	
	public void menu() {
		MenuVo topMenu = new MenuVo();
		//获得当前用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		//获得当前用户的所有权限
		List<SysPermission> list = sysUserService.findAllPermission(activeUser.getId());
		
		
	
		//准备一个Map作为寻找父层菜单的索引
		Map<String,MenuVo> parentMap = new HashMap<>();
		for (SysPermission sysPerm : list) {
			//1代表第一层菜单
			if ("1".equals(sysPerm.getPlevel())) {
				MenuVo parentMenu = new MenuVo();
				parentMenu.setId(sysPerm.getId());
				parentMenu.setIcon(sysPerm.getIcon());
				parentMenu.setMenuname(sysPerm.getName());
				parentMenu.setMenuid("1");
				parentMenu.setUrl(sysPerm.getUrl());
				//添加到顶层菜单
				topMenu.getMenus().add(parentMenu);
				//添加到索引map
				parentMap.put(sysPerm.getId(), parentMenu);
			}
		}
		//2代表第二层菜单
		for (SysPermission sysPerm : list) {
			if ("2".equals(sysPerm.getPlevel())) {
				MenuVo childMenu = new MenuVo();
				childMenu.setId(sysPerm.getId());
				childMenu.setIcon(sysPerm.getIcon());
				childMenu.setMenuid("1_1");
				childMenu.setMenuname(sysPerm.getName());
				childMenu.setUrl(sysPerm.getUrl());
				childMenu.setMenus(null); // 再往下没有子菜单，设为null
				//添加到所属的父层菜单
				String parentid = sysPerm.getParentid();
				MenuVo parentMenuvo = parentMap.get(parentid);
				if (parentMenuvo != null) {
					Set<MenuVo> menus = parentMenuvo.getMenus();
					menus.add(childMenu);
				}
			}
		}
		
		write_object(topMenu);
	}
}
