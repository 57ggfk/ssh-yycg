package cn.itcast.yycg.cgd.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.analyze.dao.SysTaskDao;
import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.cgd.dao.YycgdDao;
import cn.itcast.yycg.cgd.dao.YycgdMxDao;
import cn.itcast.yycg.cgd.dao.query.YycgdQuery;
import cn.itcast.yycg.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.cgd.pojo.YycgdQueryCustom;
import cn.itcast.yycg.cgd.service.YycgdService;
import cn.itcast.yycg.domain.po.DwGys;
import cn.itcast.yycg.domain.po.DwGysDq;
import cn.itcast.yycg.domain.po.DwWss;
import cn.itcast.yycg.domain.po.DwWsy;
import cn.itcast.yycg.domain.po.SysArea;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.SysTask;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.domain.po.YycgdMx;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.system.dao.DwGysDao;
import cn.itcast.yycg.system.dao.DwGysDqDao;
import cn.itcast.yycg.system.dao.DwWssDao;
import cn.itcast.yycg.system.dao.DwWsyDao;
import cn.itcast.yycg.system.dao.SysDictInfoDao;
import cn.itcast.yycg.ypml.dao.YpxxDao;

@Service
public class YycgdServiceImpl implements YycgdService {

	@Autowired
	private YycgdDao yycgdDao;
	@Autowired
	private DwWssDao dwWssDao;
	@Autowired
	private SysDictInfoDao sysDictInfoDao;
	@Autowired
	private DwGysDqDao dwGysDqDao;
	@Autowired
	private YpxxDao ypxxDao;
	@Autowired
	private YycgdMxDao yycgdMxDao;
	@Autowired
	private DwWsyDao dwWsyDao;
	@Autowired
	private SysTaskDao sysTaskDao;
	@Autowired
	private DwGysDao dwGysDao;

	@Override
	public Integer saveYycgd(YycgdCustom yycgdCustom) {
		// 校验是否卫生室
		DwWss dwWss = yycgdCustom.getDwWss();
		dwWss = dwWssDao.findById(dwWss.getId());
		if (dwWss == null) {
			ResultUtil.throwExcepionResult("您不是卫生室，您没有权限");
		}
		// 处理信息
		Yycgd yycgd = new Yycgd();
		BeanUtils.copyProperties(yycgdCustom, yycgd);

		// 补充信息，卫生室在对象action层提供

		// 创建时间
		yycgd.setCjsj(new Date());

		// 状态设置为未提交，采购单状态typecode=010，未提交01001
		SysDictInfo sysDictInfoByzt = new SysDictInfo("01001");
		yycgd.setSysDictInfoByzt(sysDictInfoByzt);

		// 供应商id(难点)
		String parentid = dwWss.getSysArea().getParentid();
		DwGysDq dwGysDq = dwGysDqDao.findById(parentid); // areaId是id
		yycgd.setDwGys(dwGysDq.getDwGys());

		// 保存
		return (Integer) yycgdDao.save(yycgd);

	}

	@Override
	public int getTotalRecord(YycgdQueryCustom yycgdQueryCustom) {
		return yycgdDao.getTotalRecord(getYycgQuery(yycgdQueryCustom));
	}

	@Override
	public List<Yycgd> findAll(YycgdQueryCustom yycgdQueryCustom, int offset, int limit) {
		 List<Yycgd> list = yycgdDao.findAll(getYycgQuery(yycgdQueryCustom), offset, limit);
		 
		return list;
	}

	// 查询条件封装
	private YycgdQuery getYycgQuery(YycgdQueryCustom yycgdQueryCustom) {
		YycgdQuery yycgdQuery = new YycgdQuery();

		if (yycgdQueryCustom != null) {
			// 拷贝属性，如果传入yycgdQueryCustom对象不为null
			BeanUtils.copyProperties(yycgdQueryCustom, yycgdQuery);
		}

		// 获得当前用户，根据角色设定查询条件
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String groupid = activeUser.getGroupid();

		SysDictInfo sysDictInfo = sysDictInfoDao.findById(groupid);
		String dictcode = sysDictInfo.getDictcode();
		// 根据dictcode判断单位
		if ("1".equals(dictcode) || "2".equals(dictcode)) {
			// 查询卫生局或卫生院
			DwWsy dwWsy = dwWsyDao.findById(activeUser.getSysid());
			// 获得地区
			SysArea sysArea = dwWsy.getSysArea();
			// 存入YycgdQuery
			yycgdQuery.setSysArea(sysArea);

		} else if ("3".equals(dictcode)) { // 卫生室
			DwWss dwWss = new DwWss();
			dwWss.setId(activeUser.getSysid());
			yycgdQuery.setDwWss(dwWss);
		} else if ("4".equals(dictcode)) { // 供应商
			DwGys dwGys = new DwGys();
			dwGys.setId(activeUser.getSysid());
			yycgdQuery.setDwGys(dwGys);
		}
		return yycgdQuery;
	}

	@Override
	public Yycgd findById(Integer id) {
		Yycgd yycgd = yycgdDao.findById(id);
		return yycgd;
	}

	@Override
	public void updateYyycg(YycgdCustom yycgdCustom) {
		// 校验

		// 采购单是否存在
		Yycgd yycgd = yycgdDao.findById(yycgdCustom.getId());
		if (yycgd == null) {
			ResultUtil.throwExcepionResult("采购单不存在");
		}

		// 是否卫生室自己的采购单
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String sysid = activeUser.getSysid();
		String wssid = yycgd.getDwWss().getId();
		if (!wssid.equals(sysid)) {
			ResultUtil.throwExcepionResult("您不是卫生室，或不是您的采购单");
		}

		// 状态校验：未提交01001 和 审核未通过01004 可以提交，其它不可以
		String ztId = yycgd.getSysDictInfoByzt().getId();
		if (!("01001".equals(ztId) || "01004".equals(ztId))) {
			ResultUtil.throwExcepionResult("采购单已经提交，请不要重复操作");
		}

		SysDictInfo sysDictInfoByzt = yycgdCustom.getSysDictInfoByzt(); // 获取新的状态
		if (sysDictInfoByzt != null && StringUtils.isNotBlank(sysDictInfoByzt.getId())) {

			// 表示提交
			yycgd.setTjsj(new Date()); // 设置提交时间
			yycgd.setSysDictInfoByzt(sysDictInfoByzt); // 设置状态

			// 校验采购单详情，必须有药品，并且采购量不为0
			Set<YycgdMx> allMx = yycgd.getYycgdMxes();
			if (allMx.size() < 1) {
				ResultUtil.throwExcepionResult("采购单明细必须有药品");
			}
			for (YycgdMx yycgdMx : allMx) {
				Integer cgl = yycgdMx.getCgl();
				if (cgl < 1) {
					ResultUtil.throwExcepionResult("流水号为[" + yycgdMx.getYpxx().getBm() + "]药品采购量不能为0");
				}
			}

			// 模拟任务
			SysTask sysTask = new SysTask();
			sysTask.setTaskType("01");
			sysTask.setData1("" + yycgd.getId());
			sysTask.setTaskAddDate(new Date());
			sysTaskDao.save(sysTask);
		} else {

			// 表示修改
			yycgd.setLxr(yycgdCustom.getLxr()); // 联系人
			yycgd.setLxdh(yycgdCustom.getLxdh()); // 联系电话
			yycgd.setBz(yycgdCustom.getBz()); // 备注

			yycgd.setXgsj(new Date()); // 修改时间
		}
		// yycgdDao.update(yycgd); //缓存自动与快照对比，不一致自动保存
	}

	@Override
	public void updatedispose(Integer cgdId, String gysId) {
		// 获得当前用户--供应商
		DwGys dwGys = dwGysDao.findById(gysId);
		// 获得当前采购单
		Yycgd yycgd = yycgdDao.findById(cgdId);

		// 校验
		// 1.校验:采购单存在
		if (yycgd == null) {
			ResultUtil.throwExcepionResult("当前采购单已不存在!");
		}
		// 2.校验:当前用户为供应商
		if (dwGys == null) {
			ResultUtil.throwExcepionResult("当前用户不是供应商!");
		}
		// 3.校验:当前采购单所属的供应商是否当前供应商
		// 该医药采购单所属的的供应商id
		String yycgdGysId = yycgd.getDwGys().getId();
		if (!gysId.equals(yycgdGysId)) {
			ResultUtil.throwExcepionResult("该采购单不属于当前供应商管辖!");
		}
		// 4.校验:当前采购单状态必须为审核通过且不能重复完成受理
		// 当前采购单的审核状态
		String ztId = yycgd.getSysDictInfoByzt().getId();
		if ("01005".equals(ztId)) {
			ResultUtil.throwExcepionResult("已受理的采购单不能重复受理!");
		} else if (!"01003".equals(ztId)) {
			ResultUtil.throwExcepionResult("当前采购单未通过审核,无法操作!");
		}
		// 5.校验:当前采购单中的所有明细的受理状态必须为已发货或不能发货
		Set<YycgdMx> yycgdMxes = yycgd.getYycgdMxes();
		for (YycgdMx yycgdMx : yycgdMxes) {
			String yycgdMxId = yycgdMx.getSysDictInfoCgzt().getId();
			if ("01101".equals(yycgdMxId)) {
				ResultUtil.throwExcepionResult("当前采购单中还有未受理明细!");
			}
		}
		// 修改采购单状态为完成受理
		yycgd.setSysDictInfoByzt(new SysDictInfo("01005"));
		
		// 任务02   已受理
		SysTask sysTask = new SysTask();
		sysTask.setTaskType("02");
		sysTask.setData1("" + yycgd.getId());
		sysTask.setTaskAddDate(new Date());
		sysTaskDao.save(sysTask);
	}
	
	/**
	 * 删除未提交的采购单
	 */
	@Override
	public void deleteYycgd(YycgdCustom yycgdCustom) {
		// TODO Auto-generated method stub
		//1.校验
		//1.1处理
		Yycgd yycgd = new Yycgd();
		//复制数据
		BeanUtils.copyProperties(yycgdCustom, yycgd);
		//查询该对象是否存在
		yycgd = yycgdDao.findById(yycgd.getId());
		if (yycgd == null) {
			ResultUtil.throwExcepionResult("该采购单不存在！");
		}
		//1.2校验采购单是否提交
		if(! ("01001".equals(yycgd.getSysDictInfoByzt().getId()) || "01004".equals(yycgd.getSysDictInfoByzt().getId()))  ){
			ResultUtil.throwExcepionResult("采购单已提交，不可删除");
		}
		
		//1.3校验是否是卫生室
		//DwWss dwWss = dwWssDao.findById(yycgdCustom.getDwWss().getId());
	/*	DwWss dwWss = yycgdCustom.getDwWss();
		if (dwWss == null) {
			ResultUtil.throwExcepionResult("您不是卫生室，没有权限删除！");
		}*/
		
		//1.4校验是不是这个当前用户的订单
		//获得当前用户，根据角色设定查询条件
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		String sysId = activeUser.getSysid();
		if (!yycgd.getDwWss().getId().equals(sysId)) {
			ResultUtil.throwExcepionResult("您没有权限删除");
		}
		
		//3.删除该对象
		yycgdDao.delete(yycgd);
		
	}
	
	
	/*
	 * 采购单的审核
	 * 
	 * */
	@Override
	public void updateYyycg_check(YycgdCustom yycgdCustom) {
			//校验
		
			//采购单是否存在
			Yycgd yycgd = yycgdDao.findById(yycgdCustom.getId());
			if (yycgd==null) {
				ResultUtil.throwExcepionResult("采购单不存在");
			}
			
			//获得当前用户
			ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
			//获得用户所属单位id
			String sysid = activeUser.getSysid();
			String groupid = activeUser.getGroupid();
			SysDictInfo sysDictInfo = sysDictInfoDao.findById(groupid);
			String dictcode = sysDictInfo.getDictcode();
			//根据dictcode判断单位
			if ("1".equals(dictcode) || "2".equals(dictcode)) {
				//查询卫生局或卫生院
				DwWsy dwWsy = dwWsyDao.findById(activeUser.getSysid());
				//获得卫生局或卫生院的id
				 String wsyid = dwWsy.getId();
			if (!wsyid.equals(sysid)) {
				ResultUtil.throwExcepionResult("您不是卫生院或卫生局，没有权限");
			}
			
			//状态校验：已提交未审核01002 其它不可以
			String ztId = yycgd.getSysDictInfoByzt().getId();
			if (! ("01002".equals(ztId)) ) {
				ResultUtil.throwExcepionResult("采购单还未提交或已审核完成");
			}
			
			SysDictInfo sysDictInfoByzt = yycgdCustom.getSysDictInfoByzt(); //获取新的状态
			if (sysDictInfoByzt==null ||"01002".equals(yycgdCustom.getSysDictInfoByzt().getId())) {
				ResultUtil.throwExcepionResult("您还没有审核，请先审核");
			}
			
			if (sysDictInfoByzt!=null && StringUtils.isNotBlank(sysDictInfoByzt.getId())) {
				yycgd.setShyj(yycgdCustom.getShyj());
				//表示提交
				yycgd.setShsj(new Date()); 							//设置审核时间
				yycgd.setSysDictInfoByzt(sysDictInfoByzt);				//设置状态
				
				//校验采购单详情，必须有药品，并且采购量不为0
				Set<YycgdMx> allMx = yycgd.getYycgdMxes();
				if (allMx.size()<1) {
					ResultUtil.throwExcepionResult("采购单明细必须有药品");
				}
				for (YycgdMx yycgdMx:allMx) {
					Integer cgl = yycgdMx.getCgl();
					if (cgl<1) {
						ResultUtil.throwExcepionResult("流水号为["+yycgdMx.getYpxx().getBm()+"]药品采购量不能为0");
					}
				}
				
			} else {
				//修改时间
				yycgd.setXgsj(new Date());						
			}
		}
		
	}
	


}
