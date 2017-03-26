package cn.itcast.yycg.auth.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysUser;
import cn.itcast.yycg.domain.po.util.SysUserUtil;
import cn.itcast.yycg.user.service.SysUserService;

//spring配置中配置了自定义CustomRealm
public class CustomRealm extends AuthorizingRealm{

	@Autowired
	private SysUserService sysUserService;
	
	@Override
	public String getName() {
		return "customRealm";
	}
	@Override
	//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取认证信息
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();
		
		//通过身份信息获得所有权
		List<SysPermission> allPerm = sysUserService.findAllPermission(activeUser.getId());
		//通过权限获得权限标识
		List<String> list = new ArrayList<String>();
		//模拟数据
		//list.add("user:queryuser");
		//list.add("user:add");
		//list.add("user:delete");
		
		//获得所有权限标识符 level为3且pcode有值
		for (SysPermission perm: allPerm) {
			if ("3".equals(perm.getPlevel()) && StringUtils.isNotBlank(perm.getPcode())) {
				list.add(perm.getPcode());
			}
		}
		System.out.println(list);
		//将查询结果交给shiro
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(list);
		return simpleAuthorizationInfo;
	}

	@Override
	//认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//身份信息，相当于用户名
		String principal = (String) token.getPrincipal();
		
		//通过身份信息从数据库中查询
		SysUser sysUser = sysUserService.findByUsercode(principal);
		if (sysUser==null) {
			//通知shiro用户不存在
			return null;
		}
		
		//获得密码，spring中配置了密码匹配器，shiro会按照md5匹配
		String credentials = sysUser.getPwd();
		
		//把身份信息封装到一个对象ActiveUser
		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setGroupid(sysUser.getSysDictInfoByGroupid().getId());
		activeUser.setGroupname(sysUser.getSysDictInfoByGroupid().getInfo());
		activeUser.setSysid(SysUserUtil.getDW_Id(sysUser));
		activeUser.setSysmc(SysUserUtil.getDW_MC(sysUser));
		
		//将所有信息交给shiro进行比对
		return new SimpleAuthenticationInfo(activeUser, credentials, getName());
	}

	
}
