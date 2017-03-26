package cn.itcast.yycg.user.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.SysUser;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.framework.web.result.SubmitResultInfo;
import cn.itcast.yycg.system.service.DwService;
import cn.itcast.yycg.system.service.SysDictInfoService;
import cn.itcast.yycg.user.pojo.SysUserCustom;
import cn.itcast.yycg.user.pojo.SysUserQueryCustom;
import cn.itcast.yycg.user.service.SysUserService;
import cn.itcast.yycg.user.web.vo.SysUserVo;
import cn.itcast.yycg.util.FastJsonUtil;
import cn.itcast.yycg.util.PageBean;

@Controller
@Scope("prototype")
public class SysUserAction extends BaseAction<SysUserVo> {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysDictInfoService sysDictInfoService;
	@Autowired
	private DwService dwService;

	@RequiresPermissions("user:queryuser")
	public String findById() {
		// 获取参数
		SysUserQueryCustom sysUserQueryCustom = getModel().getSysUserQueryCustom();
		if (sysUserQueryCustom != null) {
			// 通过ID查询用户
			String id = sysUserQueryCustom.getId();
			SysUser sysUser = sysUserService.findById(id);
			// 拷贝属性到自定义对象上
			SysUserCustom sysUserCustom = new SysUserCustom();
			BeanUtils.copyProperties(sysUser, sysUserCustom);
			// 自定义对象，回显数据
			getModel().setSysUserCustom(sysUserCustom);
		}
		return "findById";
	}

	public String queryuser() {
		// 根据字典类型码查询字典信息，用户类型的字典类型码：s01，查询“用户类型”的字典信息。
		List<SysDictInfo> userGroupList = sysDictInfoService.findAll("s01");
		// 保存到值对象中用于页面回显
		SysUserVo sysUserVo = this.getModel();
		sysUserVo.setUserGroupList(userGroupList);
		// 转到用查询页面
		return "queryuser";
	}

	public void queryuser_result() {
		SysUserVo sysUserVo = this.getModel();
		// List<SysUser> list =
		// sysUserService.list(sysUserVo.getSysUserQueryCustom());

		// 获得记录个数
		int total = sysUserService.getTotalReccord(sysUserVo.getSysUserQueryCustom());

		// 封装PageBean
		PageBean pageBean = new PageBean(sysUserVo.getPage(), sysUserVo.getRows(), total);

		List<SysUser> rows = sysUserService.findAll(sysUserVo.getSysUserQueryCustom(), pageBean.getStartIndex(),
				sysUserVo.getRows());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", rows);
		FastJsonUtil.write_object(getResponse(), map);
		return;
	}

	public String adduser() {
		// 根据字典类型码查询字典信息，用户类型的字典类型码：s01，查询“用户类型”的字典信息。
		List<SysDictInfo> userGroupList = sysDictInfoService.findAll("s01");
		// 根据字典查询用户状态，字典类型码：s02
		List<SysDictInfo> userStateList = sysDictInfoService.findAll("s02");
		getModel().setUserGroupList(userGroupList);
		getModel().setUserStateList(userStateList);
		return "adduser";
	}

	// 查询所属单位
	public void findDwByDictcode() {
		List<?> list = null;
		if (getModel().getSysUserQueryCustom() != null) {
			if (getModel().getSysUserQueryCustom().getSysDictInfoByGroupid() != null) {
				String dictcode = getModel().getSysUserQueryCustom().getSysDictInfoByGroupid().getDictcode();
				if (dictcode != null) {
					list = dwService.findDwByDictcode(dictcode);
				}
			}
		}
		write_object(list);
	}

	/**
	 * 用户保存
	 */
	public void adduser_submit() {
		SysUserCustom sysUserCustom = getModel().getSysUserCustom();
		sysUserService.save(sysUserCustom);
		SubmitResultInfo createSubmitResult = ResultUtil.createSubmitResult("添加成功！");
		this.write_object(createSubmitResult);
	}

	/**
	 * 用户删除
	 */
	public void deleteuser_submit() {
		String id = getModel().getSysUserCustom().getId();
		sysUserService.delete(id);
		SubmitResultInfo createSubmitResult = ResultUtil.createSubmitResult("删除成功！");
		this.write_object(createSubmitResult);
	}

	/**
	 * 用户修改页面
	 */
	public String edituser() {
		// 查询用户信息
		String id = getModel().getSysUserCustom().getId();
		SysUser sysUser = sysUserService.findById(id);
		BeanUtils.copyProperties(sysUser, getModel().getSysUserCustom());

		// 根据字典类型码查询字典信息，用户类型的字典类型码：s01，查询“用户类型”的字典信息。
		List<SysDictInfo> userGroupList = sysDictInfoService.findAll("s01");
		// 根据字典查询用户状态，字典类型码：s02
		List<SysDictInfo> userStateList = sysDictInfoService.findAll("s02");
		getModel().setUserGroupList(userGroupList);
		getModel().setUserStateList(userStateList);
		return "edituser";
	}

	/**
	 * 用户修改的保存
	 */
	public void edituser_submit() {
		sysUserService.updateUser(getModel().getSysUserCustom());
		write_object(ResultUtil.createSubmitResult("修改成功"));
	}
	
	/**
	 * 修改用户密码
	 */
	public void realChangePwd(){
		SysUserVo sysUserVo = this.getModel();
		//获取当前登录的用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		//获取当前登录用户
		String userId = activeUser.getId();
		sysUserService.updatePwd(userId,sysUserVo.getOldPassword(),sysUserVo.getNewPassword(),sysUserVo.getRelPassword());
		this.write_object(ResultUtil.createSubmitResult("修改成功!"));
	}

}
