package cn.itcast.yycg.user.dao;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.SysUser;

public interface SysUserDao extends BaseDao<SysUser> {

	/**
	 * 通过usercode登录名查询用户
	 * @param usercode
	 * @return
	 */
	SysUser findByUsercode(String usercode);

}
