package cn.itcast.yycg.cgd.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.cgd.pojo.YycgdMxQueryCustom;
import cn.itcast.yycg.cgd.pojo.YycgdQueryCustom;
import cn.itcast.yycg.cgd.service.YycgdMxService;
import cn.itcast.yycg.cgd.service.YycgdService;
import cn.itcast.yycg.cgd.web.vo.YycgdVo;
import cn.itcast.yycg.domain.po.DwGys;
import cn.itcast.yycg.domain.po.DwWss;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.domain.po.YycgdMx;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.framework.web.result.SubmitResultInfo;
import cn.itcast.yycg.system.dao.DwGysDao;
import cn.itcast.yycg.system.dao.DwGysDqDao;
import cn.itcast.yycg.system.service.SysDictInfoService;
import cn.itcast.yycg.util.MyUtil;
import cn.itcast.yycg.util.PageBean;

@Controller
@Scope("prototype")
public class YycgdAction extends BaseAction<YycgdVo> {

	@Autowired
	private YycgdService yycgdService;
	@Autowired
	private SysDictInfoService sysDictInfoService;
	@Autowired
	private YycgdMxService yycgdMxService;
	@Autowired
	private DwGysDao dwGysDao;
	@Autowired
	private DwGysDqDao dwGysDqDao;

	private YycgdVo yycgdVo = getModel();

	/**
	 * 创建采购单的页面
	 * 
	 * @return
	 */
	public String add() {

		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();

		// 生成采购单名称 : 用户名称+时间+采购单
		String mc = activeUser.getUsername() + MyUtil.yymmdddateToString(new Date()) + "采购单";
		// 存入vo
		YycgdCustom yycgdCustom = new YycgdCustom();
		yycgdCustom.setMc(mc);
		yycgdVo.setYycgdCustom(yycgdCustom);
		;

		return "add";
	}

	/**
	 * 创建采购单保存
	 */
	public void add_submit() {
		// 获得当前角色id
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		// 设置单位卫生室id
		DwWss dwWss = new DwWss();
		dwWss.setId(activeUser.getSysid());
		yycgdCustom.setDwWss(dwWss);
		// 保存采购单
		Integer yycgdid = yycgdService.saveYycgd(yycgdCustom);
		// 回显提示信息
		SubmitResultInfo createSubmitResult = ResultUtil.createSubmitResult("医药采购单创建成功");
		createSubmitResult.getResultInfo().getSysdata().put("yycgdid", yycgdid);
		write_object(createSubmitResult);
	}

	/**
	 * 采购单查询页面
	 */
	public String list() {
		// 采购单状态数据，从字典查询：采购单状态010
		List<SysDictInfo> yycgdZtList = sysDictInfoService.findAll("010");
		yycgdVo.setYycgdZtList(yycgdZtList);
		return "list";
	}

	/**
	 * 采购单查询结果，返回datagrid需要的json数据
	 */
	public void list_result() {

		// 分页查询准备
		int total = yycgdService.getTotalRecord(yycgdVo.getYycgdQueryCustom());
		PageBean pageBean = new PageBean(yycgdVo.getPage(), yycgdVo.getRows(), total);
		List<Yycgd> list = yycgdService.findAll(yycgdVo.getYycgdQueryCustom(), pageBean.getStartIndex(),
				pageBean.getPageSize());

		write_object(ResultUtil.createDataGridResult(total, list));

	}

	/**
	 * 进入编辑页面
	 */
	public String edit() {

		//药品类别
		List<SysDictInfo> yplbList = sysDictInfoService.findAll("001");
		//交易状态
		List<SysDictInfo> jyztList = sysDictInfoService.findAll("003");
		//质量层次
		List<SysDictInfo> ypzlccList = sysDictInfoService.findAll("004");
		//采购状态
		List<SysDictInfo> cgztList = sysDictInfoService.findAll("010");
		
		//回显
		yycgdVo.setYplbList(yplbList);
		yycgdVo.setJyztList(jyztList);
		yycgdVo.setYpzlccList(ypzlccList);
		yycgdVo.setCgztList(cgztList);

		// 查询采购单
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		Yycgd yycgd = yycgdService.findById(yycgdCustom.getId());
		// 存入vo回显
		yycgdCustom = new YycgdCustom();
		BeanUtils.copyProperties(yycgd, yycgdCustom);
		yycgdVo.setYycgdCustom(yycgdCustom);
		return "edit";
	}

	/**
	 * 指定采购单的明细
	 */
	public void yycgdmx_result() {
		// 查询条件医药采购单
		YycgdMxQueryCustom yycgdMxQueryCustom = yycgdVo.getYycgdMxQueryCustom();
		//查询总条数
		int total = yycgdMxService.getTotalRecord(yycgdMxQueryCustom);

		PageBean pageBean = new PageBean(yycgdVo.getPage(),yycgdVo.getRows(),total);
		//查询该采购单下的所有的药品信息
		List<YycgdMx> rows = yycgdMxService.findAll(yycgdMxQueryCustom, pageBean.getStartIndex(), pageBean.getPageSize());

		write_object(ResultUtil.createDataGridResult(total, rows));
	}

	/**
	 * 编辑并保存
	 */
	public void edit_submit() {
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		yycgdService.updateYyycg(yycgdCustom);
		write_object(ResultUtil.createSubmitResult("保存成功"));
	}

	/**
	 * 编辑并提交
	 */
	public void submit() {
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();

		// 设置状态为已提交未审核id:01002 dictcode:2 typecode:010
		yycgdCustom.setSysDictInfoByzt(new SysDictInfo("01002"));
		yycgdService.updateYyycg(yycgdCustom);
		write_object(ResultUtil.createSubmitResult("已提交等待审核"));
	}

	/**
	 * 采购单明细中添加药品
	 * 
	 * @return
	 */
	public String addyycgdmx() {
		// 药品类别
		List<SysDictInfo> yplbList = sysDictInfoService.findAll("001");
		// 交易状态
		List<SysDictInfo> jyztList = sysDictInfoService.findAll("003");
		// 质量层次
		List<SysDictInfo> ypzlccList = sysDictInfoService.findAll("004");
		// 回显
		yycgdVo.setYplbList(yplbList);
		yycgdVo.setJyztList(jyztList);
		yycgdVo.setYpzlccList(ypzlccList);

		return "addyycgdmx";
	}

	/**
	 * 给指定采购单添加明细商品
	 */
	public void addyycgdmx_submit() {
		// 获取参数
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		// 保存
		yycgdMxService.saveYycgdMxList(yycgdCustom.getId(), yycgdVo.getYpxxids());
		// 成功回显
		write_object(ResultUtil.createSubmitResult("添加成功"));
	}

	/**
	 * 明细药品采购量保存
	 */
	public void yycgdmxcgl_submit() {
		// 获取参数
		// Integer yycgdid = yycgdVo.getYycgdCustom().getId();
		String[] yycgdmxIds = yycgdVo.getYycgdmxIds();
		Integer[] yycgdmxCgls = yycgdVo.getYycgdmxCgls();
		yycgdMxService.saveYycgdMxCgls(yycgdmxIds, yycgdmxCgls);
		write_object(ResultUtil.createSubmitResult("采购量保存成功"));
	}

	/**
	 * 明细药品批量删除
	 */ 
	public void yycgdmxdel_submit() {
		// 获取参数
		// Integer yycgdid = yycgdVo.getYycgdCustom().getId();
		String[] yycgdmxIds = yycgdVo.getYycgdmxIds();
		yycgdMxService.deleteYycgdMxIds(yycgdmxIds);
		write_object(ResultUtil.createSubmitResult("指定药品删除成功"));
	}

	/**
	 * 采购单受理:跳转到采购单信息页面,显示采购单信息
	 * 
	 * @return
	 */
	public String dispose() {
		// 获得条件数据
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		// 获得采购单
		Integer cgdId = yycgdVo.getYycgdMxCustom().getYycgd().getId();
		Yycgd yycgd = yycgdService.findById(cgdId);
		// 获得当前登录用户(供应商)的单位id
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String gysId = activeUser.getSysid();
		// 通过供应商id查询供应商
		DwGys gys = dwGysDao.findById(gysId);

		// 校验1:当前用户是否供应商
		if (gys == null) {
			ResultUtil.throwExcepionResult("当前用户不是供应商!");
		}
		// 校验2:采购单存在
		if (yycgd == null) {
			ResultUtil.throwExcepionResult("当前采购单已不存在!");
		}
		// 校验3:当前采购单属于当前用户管辖
		// 该医药采购单所属的的供应商id
		String yycgdGysId = yycgd.getDwGys().getId();
		if (!gysId.equals(yycgdGysId)) {
			ResultUtil.throwExcepionResult("该采购单不属于当前供应商管辖!");
		}
		// 校验4:只能查看已审核和已受理的采购单
		String ztId = yycgd.getSysDictInfoByzt().getId();
		if (!("01003".equals(ztId) || "01005".equals(ztId))) {
			ResultUtil.throwExcepionResult("当前采购单未通过审核,无法进行操作!");
		}

		// 查询所有的药品类别
		List<SysDictInfo> yplbList = sysDictInfoService.findAll("001");
		yycgdVo.setYplbList(yplbList);
		// 查询所有的交易状态
		List<SysDictInfo> jyztList = sysDictInfoService.findAll("003");
		yycgdVo.setJyztList(jyztList);
		// 查询所有的采购状态
		List<SysDictInfo> cgztList = sysDictInfoService.findAll("011");
		yycgdVo.setCgztList(cgztList);
		// 查询所有供货状态
		List<SysDictInfo> ghztList = sysDictInfoService.findAll("008");
		yycgdVo.setGhztList(ghztList);

		// 将采购单存入值栈
		yycgdCustom = new YycgdCustom();
		yycgd = yycgdService.findById(cgdId);
		BeanUtils.copyProperties(yycgd, yycgdCustom);
		yycgdVo.setYycgdCustom(yycgdCustom);

		return "dispose";
	}

	/**
	 * 采购单受理:完成采购单受理的方法
	 */
	public void dispose_submit() {
		// 获得条件数据
		YycgdMxQueryCustom yycgdMxQueryCustom = yycgdVo.getYycgdMxQueryCustom();
		// 获得采购单id
		Integer cgdId = yycgdMxQueryCustom.getYycgd().getId();
		// 获得当前登录用户的单位id
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String gysId = activeUser.getSysid();

		// 调用service
		yycgdService.updatedispose(cgdId, gysId);

		// 返回成功提示信息
		this.write_object(ResultUtil.createSubmitResult("编号为[" + cgdId + "]的采购单已成功受理!"));
	}

	/**
	 * 保存药品发货状态
	 */
	public void saveyycgdmxfhzt() {
		Integer cgdId = yycgdVo.getYycgdCustom().getId();
		// 药品状态ID
		String[] yycgdmxztIds = yycgdVo.getYycgdmxztIds();
		// 药品明细ID
		String[] yycgdmxIds = yycgdVo.getYycgdmxIds();
		yycgdMxService.saveYycgdMxZt(cgdId, yycgdmxIds, yycgdmxztIds);
		this.write_object(ResultUtil.createSubmitResult("保存" + yycgdmxIds.length + "条药品发货状态成功"));
	}

	/**
	 * 采购单受理:跳转到采购单列表页面
	 * @return
	 */
	public String disposelist() {
		// 获得当前用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String sysId = activeUser.getSysid();
		DwGys dwGys = dwGysDao.findById(sysId);
		if (dwGys == null) {
			ResultUtil.throwExcepionResult("您没有权限进行受理操作!");
		}
		// 采购单状态数据，从字典查询：采购单状态010
		List<SysDictInfo> list = sysDictInfoService.findAll("010");
		List<SysDictInfo> yycgdZtList = new ArrayList<SysDictInfo>();
		for (SysDictInfo sysDictInfo : list) {
			if ("01005".equals(sysDictInfo.getId())) {
				sysDictInfo.setInfo("已受理");
				yycgdZtList.add(sysDictInfo);
			}
			if ("01003".equals(sysDictInfo.getId())) {
				sysDictInfo.setInfo("未受理");
				yycgdZtList.add(sysDictInfo);
			}
		}
		yycgdVo.setYycgdZtList(yycgdZtList);
		return "disposelist";
	}
	
	/**
	 * 采购单受理:返回datagrid数据
	 * @return
	 */
	public void disposelist_result() {
		// 当前登录用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String gysId = activeUser.getSysid();
		YycgdQueryCustom yycgdQueryCustom = yycgdVo.getYycgdQueryCustom();
		if (yycgdQueryCustom == null) {
			yycgdQueryCustom = new YycgdQueryCustom();
		}
		
		// 分页查询准备
		int total = yycgdService.getTotalRecord(yycgdVo.getYycgdQueryCustom());
		PageBean pageBean = new PageBean(yycgdVo.getPage(), yycgdVo.getRows(), total);
		// 只能查到该供应商所属的采购单
		DwGys dwGys = dwGysDao.findById(gysId);
		yycgdQueryCustom.setDwGys(dwGys);

		List<Yycgd> list = yycgdService.findAll(yycgdQueryCustom, pageBean.getStartIndex(), pageBean.getPageSize());

		write_object(ResultUtil.createDataGridResult(total, list));

	}
	
	/**
	 * 根据用户的权限查询出来所需要的采购单列表 并完成页面跳转
	 */
	
	public String checklist(){
		
		//将查询采购单状态查找找出来 并返还给页面
		List<SysDictInfo> cgdztallList = sysDictInfoService.findAll("010");
		List<SysDictInfo> cgdztlist = new ArrayList<SysDictInfo>();
		for (SysDictInfo sysDictInfo : cgdztallList) {
			if("01002".equals(sysDictInfo.getId())||"01003".equals(sysDictInfo.getId())){
				cgdztlist.add(sysDictInfo);
			}
		}
		yycgdVo.setYycgdZtList(cgdztlist);
		//获得当前用户，根据角色设定查询条件
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String groupid = activeUser.getGroupid();
		SysDictInfo sysDictInfo = sysDictInfoService.findById(groupid);
		String dictcode = sysDictInfo.getDictcode();
		if("1".equals(dictcode)||"2".equals(dictcode)){
			//完成页面跳转
			return "checklist";
		}else{
			//提示信息
			ResultUtil.throwExcepionResult("您没有权限对采购单进行操作");
			return null;
		}
	}
	
	/**
	 * 将对应权限的用户的采购单查询出来，并返还给页面
	 */
	public void checklist_result(){
		//获得数据
		//1判断用户 根据用户的权限加载对应的采购单信息
		//获得当前用户，根据角色设定查询条件
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String groupid = activeUser.getGroupid();
		SysDictInfo sysDictInfo = sysDictInfoService.findById(groupid);
		String dictcode = sysDictInfo.getDictcode();
		if("1".equals(dictcode)||"2".equals(dictcode)){
			//完成查询
			//2分页查找
			int total = yycgdService.getTotalRecord(yycgdVo.getYycgdQueryCustom());
			PageBean pageBean = new PageBean(yycgdVo.getPage(), yycgdVo.getRows(), total);
			List<Yycgd> rows = yycgdService.findAll(yycgdVo.getYycgdQueryCustom(), pageBean.getStartIndex(), pageBean.getPageSize());
			//3返还页面数据
			this.write_object(ResultUtil.createDataGridResult(total, rows));
		}else{
			//提示信息
			ResultUtil.throwExcepionResult("您没有权限对采购单进行操作");
		}
	}
	
	/**
	 * 删除采购单
	 */
	public void delete(){
		//获取参数
		yycgdService.deleteYycgd(yycgdVo.getYycgdCustom());
		write_object(ResultUtil.createSubmitResult("删除采购单成功！"));
		
	}

	
	/**
	 * 进入审核页面
	 */
	public String check() {
		//查询采购单
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		Yycgd yycgd = yycgdService.findById(yycgdCustom.getId());
		//存入vo回显
		yycgdCustom = new YycgdCustom();
		BeanUtils.copyProperties(yycgd, yycgdCustom);
		yycgdVo.setYycgdCustom(yycgdCustom);
		return "check";
	}
	
	
	/**
	 * 提交审核操作，通过或不通过
	 */
	public void check_submit() {
		YycgdCustom yycgdCustom = yycgdVo.getYycgdCustom();
		
		//设置状态为已提交通过id:01003 dictcode:3 typecode:010
		//yycgdCustom.setSysDictInfoByzt(new SysDictInfo("01003"));
		yycgdService.updateYyycg_check(yycgdCustom);
		//根据不同的状态选择，给出不同的提示
		String dictId = yycgdCustom.getSysDictInfoByzt().getId();
		
		if ("01003".equals(dictId)) {				//审核通过
			write_object(ResultUtil.createSubmitResult("已审核，等待供应商受理"));
		} else if ("01004".equals(dictId)){			//审核不通过
			write_object(ResultUtil.createSubmitResult("审核未通过，等待卫生室修改"));
		} else {
			ResultUtil.throwExcepionResult("您无权进行此操作");
		}
	}
	
	/**
	 * 采购单明细jsp
	 * @return
	 */
	public String viewyycgdmx() {
		
		return "viewyycgdmx";
	}
	
	

}
