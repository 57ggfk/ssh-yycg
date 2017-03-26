package cn.itcast.yycg.system.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.auth.web.vo.AuthVo;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.SysConf;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.system.service.DwService;
import cn.itcast.yycg.system.service.SysConfService;
import cn.itcast.yycg.util.PageBean;
@Controller
public class SystemAction extends BaseAction<AuthVo>{
	@Autowired
	private SysConfService sysConfService;
	@Autowired
	private DwService dwService;
	//打开系统管理页面
	public String systems(){
		return "systems";
	}
	//异步的从数据库中获取系统信息
	public void sysconf(){
		AuthVo authVo = this.getModel();
		//获取用户信息
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		//dwService.findAllByWsy(activeUser.getSysid());
		//调用service层获取数据
		Integer total = sysConfService.findCount(authVo.getSysConf());
		PageBean pageBean = new PageBean(authVo.getPage(), authVo.getRows(), total);
		
		List<SysConf> rows = sysConfService.findAll(authVo.getSysConf(), pageBean.getStartIndex(), pageBean.getPageSize());
		this.write_object(ResultUtil.createDataGridResult(total, rows));
	}
	
	
	//异步提交系统设置数据
	public void systemSubmit_submit(){
		AuthVo authVo = this.getModel();
		//获取用户信息
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		//获取用户单位id判断是否能修改系统配置
		String sysid = activeUser.getSysid();
		//首先获取id判断该id是否为空
		String[] sysConfIds = authVo.getSysConfId();
		if(authVo.getSysConfId()==null){
			ResultUtil.throwExcepionResult("你没有修改任何东西啊");
		}
		for (String sysConfId : sysConfIds) {
			//在保存之前先查询数据库,将未修改的数据存到session中
			SysConf sysConf = sysConfService.findById(sysConfId);
			String recover = (String) ServletActionContext.getRequest().getSession().getAttribute("recover");
			//判断是否值
			if(recover==null){
				//直接将查询到的数据传到session中去
				recover = sysConf.getId()+":"+sysConf.getValue();
			}else{
				Map<String,String> map = new HashMap<>();
				//先剪切,判断是否含有指定的id;
				String[] split = recover.split(",");
				for (String string : split) {
					String[] split2 = string.split(":");
					//先判断id是否与要添加的重合,将获取到的id添加进去
					if(split2.equals(sysConf.getId())){
						map.put(split2[0], sysConf.getId());
						continue;
					}
					//将剪切的键和值存储到map集合中
					map.put(split2[0], split2[1]);
					//如果都不想等的话就将该数据的id和value存入到map中
					map.put(sysConf.getId(), sysConf.getValue());
				}
					//遍历map
					for(Map.Entry<String, String> mp : map.entrySet()){
						String id = mp.getKey();
						String value = mp.getValue();
						//拼接
						recover = recover+","+id+":"+value;
					}
			}
			recover = recover+","+sysConf.getId()+":"+sysConf.getValue();
			//.1将recover重新存入到session中去
			//.01获取session对像
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("recover",recover);
		}
		
		//将数据传递到service层进行数据处理
		sysConfService.updateForValue(sysid,authVo.getSysConfId(),authVo.getSysConfValue());
		//前台保存成功的提示
		this.write_object(ResultUtil.createSubmitResult("已经保存成功了哟!能恢复30分钟之内修改的东西哦"));
	}
	
	//恢复功能的实现
	public void findRecover(){
		AuthVo authVo = this.getModel();
		String[] ArrSysConfId = authVo.getSysConfId();
		String sysConfId = ArrSysConfId[0];
		Object objRecover = ServletActionContext.getRequest().getSession().getAttribute("recover");
		String strRecover = (String) objRecover;
		//判断recover是否为空
		if(strRecover==null){
			ResultUtil.throwExcepionResult("哎呀,超时了");
		}
		String[] split = strRecover.split(",");
		for (String confIdValue : split) {
			String[] split2 = confIdValue.split(":");
			//获取id
			String id = split2[0];
			String value = split2[1];
			if(id.equals(sysConfId)){
				//将该id的值存入到数据库中去
				sysConfService.update(sysConfId,value);
			}
		}
		this.write_object(ResultUtil.createSubmitResult("恢复成功!"));
	}
}
