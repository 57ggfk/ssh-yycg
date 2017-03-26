package cn.itcast.yycg.perm.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.domain.po.SysPermission;
import cn.itcast.yycg.domain.po.SysRole;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.perm.pojo.SysPermCustom;
import cn.itcast.yycg.perm.pojo.SysPermissionCustom;
import cn.itcast.yycg.perm.service.SysPermissionService;
import cn.itcast.yycg.perm.service.SysRoleService;
import cn.itcast.yycg.perm.web.vo.PermVo;
import cn.itcast.yycg.perm.web.vo.TreeVo;
import cn.itcast.yycg.util.PageBean;

@Controller
@Scope("prototype")
public class PermAction extends BaseAction<PermVo> {
	
	private static final long serialVersionUID = -3158872697879537158L;
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysPermissionService sysPermissionService;
	
	//首先获取权限表页面
	public String rolelist(){
		return "rolelist";
	}
	//异步的获取权限信息
	
	public void rolelist_result(){
		
		PermVo permVo = this.getModel();
		
		int total = this.sysRoleService.getTotalRecord(permVo.getSysRoleQueryCustom());
		
		PageBean pageParameter = new PageBean(permVo.getPage(), permVo.getRows(), total);
		
		List<SysRole> rows = sysRoleService.findAll(permVo.getSysRoleQueryCustom() , pageParameter.getStartIndex(),pageParameter.getPageSize());
		
		this.write_object(ResultUtil.createDataGridResult(total, rows));
	}
	//显示树形结构
	public String roleauth(){
		
		PermVo permVo = this.getModel();
		
		SysRole sysRole = sysRoleService.findById(permVo.getSysRoleQueryCustom().getId());
		
		permVo.setSysRole(sysRole);
		
		return "roleauth";
	}
	//异步的获取sys_permission表
	public void permission_result(){
		
		PermVo permVo = this.getModel();
		
		//1 获得所有权限
		List<SysPermission> allPerm = sysPermissionService.findAll();
		
		//2 获得指定角色的所有权限,也就是说通过判断是供应商还是卫生室,卫生局等等那个角色
		List<SysPermission> roleAllPerm = sysPermissionService.findAllByRoleId(permVo.getSysRoleQueryCustom().getId());
		
		//3 生成 List<TreeVo>
		// * 提供缓存 key=permId，value=TreeVo，方便孩子找到爹
		Map<String, TreeVo> cacheMap = new HashMap<>();
		
		
		// * 拼凑tree需要的数据
		List<TreeVo> treeList = new ArrayList<>();
		for (SysPermission sysPerm : allPerm) {
			TreeVo treeVo = new TreeVo(); 
			treeVo.setId(sysPerm.getId());
			treeVo.setText(sysPerm.getName());
			if(roleAllPerm.contains(sysPerm)){
				treeVo.setChecked("checked");
			}
			TreeVo parentTreeVo = cacheMap.get(sysPerm.getParentid());
			if(parentTreeVo == null){
				treeList.add(treeVo);
			} else {
				parentTreeVo.getChildren().add(treeVo);
				//如果有子元素，将父目录选择状态取消，交予tree自己控制
				parentTreeVo.setChecked(null);
			}
			cacheMap.put(sysPerm.getId(), treeVo);
		}
		
		
		// 响应json
		this.write_object(treeList);
		
		
	}
	
	public void roleauthsubmit(){
		
		PermVo permVo = this.getModel();
		
		
		sysPermissionService.updatePermission(permVo.getSysRoleCustom().getId(),permVo.getAddPermIds());
		
		
		this.write_object(ResultUtil.createSubmitResult("权限已更新"));
		
		
	}
	
	public String addSibling(){
		PermVo permVo = this.getModel();
		//通过id查询兄弟权限信息
		SysPermission sysPermission=sysPermissionService.findById(permVo.getSysPermissionCustom().getId());
		//准备页面数据
		SysPermissionCustom sysPermissionCustom=new SysPermissionCustom(); 
		BeanUtils.copyProperties(sysPermission, sysPermissionCustom);
		//准备新权限的id
		//1.先获取父亲的id
		String parentId=sysPermissionCustom.getParentid();
		//获取父亲孩子的个数：
		int count=sysPermissionService.findChildCount(parentId);
		sysPermissionCustom.setUrl("");
		sysPermissionCustom.setPcode("");
		//创建id值
		String newid="1";
	    if(count<10){
			newid=parentId+"0";
		} 
		++count;
		newid=newid+count+"."; 
		
		if (newid.equals(permVo.getSysPermissionCustom().getId())) {
			
			newid=newid+(count+1)+".";
			
		}
			
		sysPermissionCustom.setId(newid);	
		
		permVo.setSysPermissionCustom(sysPermissionCustom);
		return "addSibling";
	}
	
	
	public void add_submit(){
		PermVo permVo = this.getModel();
		
		SysPermissionCustom sysPermissionCustom=permVo.getSysPermissionCustom();
		sysPermissionCustom.setShoworder(sysPermissionCustom.getId());
		
	
		sysPermissionService.savePermission(sysPermissionCustom);
		this.write_object(ResultUtil.createSubmitResult("添加权限成功了"));
	}
	
	/**
	 * 添加子节点
	 * @return
	 */
	public String addChild(){
		PermVo permVo = this.getModel();
		
		//通过id查询父亲权限信息
		String parentId=permVo.getSysPermissionCustom().getId();
		SysPermission sysPermission=sysPermissionService.findById(parentId);
		
		//准备页面数据
		SysPermissionCustom sysPermissionCustom=new SysPermissionCustom (); 
		BeanUtils.copyProperties(sysPermission, sysPermissionCustom);
		
		//给孩子设置父亲的id
		sysPermissionCustom.setParentid(parentId);
		
		//设置不是菜单
		sysPermissionCustom.setIsmenu("0");
		//设置孩子的等级
		Integer parentLevel=Integer.parseInt(sysPermission.getPlevel());
		parentLevel++;//父亲的等级加1
		String childLevel=parentLevel.toString();
		sysPermissionCustom.setPlevel(childLevel);
		
		//准备新权限的id
		//1.先获取父亲的id
		
		//获取父亲孩子的个数：
		 int count=sysPermissionService.findChildCount(parentId);
		
		
		sysPermissionCustom.setUrl("");
		sysPermissionCustom.setPcode("");
		
		
		//创建id值
		String newid=null;
		if(count<10){
			newid=parentId+"0";
		}
		count++;
		newid=newid+count+".";
		sysPermissionCustom.setId(newid);	
		
		permVo.setSysPermissionCustom(sysPermissionCustom);
		return "addSibling";
	}
	
	public String list(){
		return "list";
	}
	
	public void allperm_result(){
		List<SysPermission> rows=sysPermissionService.findAll();
		int total=rows.size();
		this.write_object(ResultUtil.createDataGridResult(total, rows));
	}
	
	public String editPerm(){
		
		PermVo permVo = this.getModel();
		
		//通过id查询兄弟权限信息
		SysPermission sysPermission=sysPermissionService.findById(permVo.getSysPermissionCustom().getId());
		
		//准备页面数据
		SysPermissionCustom sysPermissionCustom=new SysPermissionCustom (); 
		BeanUtils.copyProperties(sysPermission, sysPermissionCustom);
		permVo.setSysPermissionCustom(sysPermissionCustom);
		return "editPerm";
	}
	/**
	 * 修改权限提交
	 */
	public void edit_submit(){
		PermVo permVo = this.getModel();
		sysPermissionService.update(permVo.getSysPermissionCustom());
		System.out.println("更新成功了");
		this.write_object(ResultUtil.createSubmitResult("更新成功了！！！"));
	}
	
	/**
	 * 删除本节点
	 */
	public void deletePerm_submit(){
		
		PermVo permVo = this.getModel();
		//通过id查询兄弟权限信息
		SysPermission sysPermission=sysPermissionService.findById(permVo.getSysPermissionCustom().getId());
		
		// 获取登录用户名称
	   // ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		sysPermissionService.delete(sysPermission);
		this.write_object(ResultUtil.createSubmitResult("删除成功！！！"));
	}
	
	/**
	 * 删除本节点及其子节点
	 */
	public void deletePerm_all_submit(){
		
		System.out.println("开始删除了！！");
		ResultUtil.createSubmitResult("删除成功了！！！");
	}

	public String permList(){
		return "permList";
	}
	
	public void permissionList_result(){
		PermVo permVo = this.getModel();
		int total = sysPermissionService.findCount(permVo.getSysPermQueryCustom());
		PageBean pageBean = new PageBean(permVo.getPage(), permVo.getRows(), total);
		List<SysPermission> rows = sysPermissionService.findAllList(permVo.getSysPermQueryCustom(),pageBean.getStartIndex(),pageBean.getPageSize());
		this.write_object(ResultUtil.createDataGridResult(total, rows));
	}
	
	public void updatePermission_submit(){
		PermVo permVo = this.getModel();
		System.out.println(permVo.getAllDataString());
		String allDataString = permVo.getAllDataString();
		//切割字符串,获得单列数据
		String[] split = allDataString.split(",");
		//遍历数组
		for (String oneCell : split) {
			//判断切割是否异常
			if("".equals(oneCell)){
				continue;
			}
			//再切割,获得单个数据
			String[] oneCell1 = oneCell.split(":");
			//遍历数组
			if(oneCell1[0].equals("A")){    //id1  name2   isused6  
				ResultUtil.throwExcepionResult("对不起,数据异常");
			}
			if(oneCell1[1].equals("A")){    //id1  name2   isused6  
				ResultUtil.throwExcepionResult("对不起,name不能为空");
			}
			if(oneCell1[5].equals("A")){    //id1  name2   isused6  
				ResultUtil.throwExcepionResult("对不起,isused不能为空");
			}
			if(oneCell1[5].substring(0, oneCell1[5].length()-1).length()!=1){
				ResultUtil.throwExcepionResult("对不起,isused只能为一个字符");
			}
			if(oneCell1[6].substring(0, oneCell1[6].length()-1).length()!=1){
				ResultUtil.throwExcepionResult("对不起,ismenu只能为单个数字");
			}
			if(oneCell1[7].substring(0, oneCell1[7].length()-1).length()>1){
				ResultUtil.throwExcepionResult("对不起,plevel只能为单个数字");
			}
			//先查询id,是否重存在
			String id = oneCell1[0].substring(0, oneCell1[0].length()-1);
			SysPermission sysPermission =
					sysPermissionService.findById(id);
			if(sysPermission==null){
				ResultUtil.throwExcepionResult("对不起,id异常冲突");
			}
			SysPermCustom sysPermCustom = permVo.getSysPermCustom();
			String name = oneCell1[1].substring(0, oneCell1[1].length()-1).trim();
			if(name!=null){
				sysPermission.setName(name);
			}
			String url = oneCell1[2].substring(0, oneCell1[2].length()-1).trim();
			if(url !=null){
				sysPermission.setUrl(url);
			}
			String icon = oneCell1[3].substring(0, oneCell1[3].length()-1).trim();
			if(icon!=null){
				sysPermission.setIcon(icon);
			}
			String showWorder = oneCell1[4].substring(0, oneCell1[4].length()-1).trim();
			if(showWorder!=null){
				sysPermission.setShoworder(showWorder);
			}
			String isused = oneCell1[5].substring(0, oneCell1[5].length()-1).trim();
			if(isused!=null){
				sysPermission.setIsused(isused);
			}
			String ismenu = oneCell1[6].substring(0, oneCell1[6].length()-1).trim();
			if(ismenu!=null){
				sysPermission.setIsmenu(ismenu);
			}
			String plevel = oneCell1[7].substring(0, oneCell1[7].length()-1).trim();
			if(plevel!=null){
				sysPermission.setPlevel(plevel);
			}
			String pcode = oneCell1[8].substring(0, oneCell1[8].length()-1).trim();
			if(pcode!=null){
				sysPermission.setPcode(pcode);
			}
			String vchar1 = oneCell1[9].substring(0, oneCell1[8].length()-1).trim();
			if(vchar1!=null){
				sysPermission.setVchar1(vchar1);
			}
			sysPermissionService.updateAll(sysPermission);
		}
		
		this.write_object(ResultUtil.createSubmitResult("修改成功!"));
	}

	//删除用户权限
	public void deletePermission(){
		PermVo permVo = this.getModel();
		String id = permVo.getSysPermCustom().getId();
		if(id==null){
			this.write_object(ResultUtil.createSubmitResult("删除失败!"));
		}
		//传到service层进行业务处理
		sysPermissionService.deleteById(id);
		this.write_object(ResultUtil.createSubmitResult("删除成功!"));
	}
	
	//添加数据
	public void insertPermission_submit(){
		PermVo permVo = this.getModel();
		//获取字符串
		String allDataString = permVo.getAllDataString();
		//切割数据
		String[] strings = allDataString.split(":");
		//将数据传到service层进行数据校验
		sysPermissionService.addData(strings);
		//添加成功的前台显示
		this.write_object(ResultUtil.createSubmitResult("添加成功!"));
		
	}
}
