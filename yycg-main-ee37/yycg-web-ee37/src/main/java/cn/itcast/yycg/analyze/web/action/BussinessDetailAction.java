/**
 * 
 */
package cn.itcast.yycg.analyze.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.analyze.dao.YybusinessDao;
import cn.itcast.yycg.analyze.pojo.YybusinessQueryCustom;
import cn.itcast.yycg.analyze.service.YybusinessService;
import cn.itcast.yycg.analyze.web.vo.YybusinessVo;
import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Yybusiness;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.system.dao.SysDictInfoDao;

/**
 * @author jiang
 * @CreateDate:2016年12月21日
 */
@Controller
@Scope("prototype")
public class BussinessDetailAction  extends BaseAction<YybusinessVo> {

	@Resource
	private SysDictInfoDao sysDictInfoDao;
	@Resource
	private YybusinessService yybusinessService;

	
	public String bussinessDetailUI(){	
		
		//获取用户的身份,以便于在前台显示不同的状态
		
		/*ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		
		////获取用户的信息
		//获取用的分组信息 判断是供应商还是卫生院还是卫生室
		String groupid = activeUser.getGroupid();			
		if(groupid.equals("s0101")||groupid.equals("s0102")){	
			List<SysDictInfo> ypmxzt = sysDictInfoDao.findByTypeCode("010");
			this.set("ypmxzt", ypmxzt);
		}
		else if(groupid.equals("s0103")){
			//卫生室
			List<SysDictInfo> ypmxzt = sysDictInfoDao.findByTypeCode("011");
			this.set("ypmxzt", ypmxzt);
			
		}else if (groupid.equals("s0104")) {
			//供应商
			List<SysDictInfo> ypmxzt = sysDictInfoDao.findByTypeCode("012");
			this.set("ypmxzt", ypmxzt);
		}else{
			//管理员   不应该加的,演示使用 ,Zt(状态)是不是很蛋疼?
			List<SysDictInfo> dwWsyZt = sysDictInfoDao.findByTypeCode("010");
			List<SysDictInfo> dwWssZt = sysDictInfoDao.findByTypeCode("011");
			List<SysDictInfo> dwGysZt = sysDictInfoDao.findByTypeCode("012");
			List<SysDictInfo> ypmxzt = new ArrayList<>();
			ypmxzt.addAll(dwWsyZt);
			ypmxzt.addAll(dwWssZt);
			ypmxzt.addAll(dwGysZt);
			this.set("ypmxzt", ypmxzt);
		}*/
			
		List<SysDictInfo> ypmxzt = sysDictInfoDao.findByTypeCode("011");
		this.set("ypmxzt", ypmxzt);
		
		/*
		
		//管理员   不应该加的,演示使用 ,Zt(状态)是不是很蛋疼?
		List<SysDictInfo> dwWsyZt = sysDictInfoDao.findByTypeCode("010");
		List<SysDictInfo> dwWssZt = sysDictInfoDao.findByTypeCode("011");
		List<SysDictInfo> dwGysZt = sysDictInfoDao.findByTypeCode("012");
		List<SysDictInfo> ypmxzt = new ArrayList<>();
		ypmxzt.addAll(dwWsyZt);
		ypmxzt.addAll(dwWssZt);
		ypmxzt.addAll(dwGysZt);
		this.set("ypmxzt", ypmxzt);*/
		
		//查询采购状态用于回显 药品明细状态
		
		return "bussinessDetailUI";
	}
	
	/**
	 * 查询采购单详情(通过条件,分页信息)
	 * 
	 * */
	public void bussinessDetail(){
		//根据条件查询所有
		YybusinessVo vo = getModel();
		YybusinessQueryCustom yybusinessQueryCustom = vo.getYybusinessQueryCustom();
		//防止空指针异常
		if (yybusinessQueryCustom==null) {
			yybusinessQueryCustom = new YybusinessQueryCustom();
		}
		//获取查询的信息	 page为当前页 rows为条数	
		List<Yybusiness> list = yybusinessService.findBussinessDetail(yybusinessQueryCustom,vo.getPage(),vo.getRows());
		//将结果写回客户端
		this.write_object(ResultUtil.createDataGridResult(list.size(), list));	
	}

}
