package cn.itcast.yycg.analyze.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.analyze.dao.SysTaskDao;
import cn.itcast.yycg.analyze.dao.SysTaskFinisDao;
import cn.itcast.yycg.analyze.dao.YybusinessDao;
import cn.itcast.yycg.analyze.service.AnalyzeGetherService;
import cn.itcast.yycg.cgd.dao.YycgdDao;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.SysDictType;
import cn.itcast.yycg.domain.po.SysTask;
import cn.itcast.yycg.domain.po.SysTaskFinis;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yybusiness;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.domain.po.YycgdMx;
import cn.itcast.yycg.ypml.dao.YpxxDao;

@Service
public class AnalyzeGetherServiceImpl implements AnalyzeGetherService {

	@Autowired
	private SysTaskDao sysTaskDao;
	@Autowired
	private SysTaskFinisDao sysTaskFinisDao;
	@Autowired
	private YybusinessDao yybusinessDao;
	@Autowired
	private YycgdDao yycgdDao;
	
	@Override
	/**
	 * 采购单审核
	 * 
	 * */
	public void saveTask01(SysTask sysTask) {
		//1.向交易明细表添加数据
		//1.1获得任务数据：采购单id转换为整型
		Integer yycgdId = Integer.parseInt(sysTask.getData1());
		//1.2通过id查询采购单
		Yycgd yycgd = yycgdDao.findById(yycgdId);
		//1.3获得明细
		Set<YycgdMx> allMx = yycgd.getYycgdMxes();
		
		for (YycgdMx yycgdMx : allMx) {
			//一个明细对应一条交易明细记录
			//通过药品信息 ypxx 和 yycgd 作唯一条件去一起去查询数据库 
			Yybusiness yybusiness = yybusinessDao.findByUnique(yycgdMx.getYpxx(),yycgd);
			if (yybusiness == null ) {
				yybusiness = new Yybusiness();
			}
			
			yybusiness.setYycgd(yycgd);
			yybusiness.setDwWss(yycgd.getDwWss());
			yybusiness.setDwGys(yycgd.getDwGys());
			yybusiness.setYpxx(yycgdMx.getYpxx());
			yybusiness.setZbjg(yycgdMx.getZbjg());
			yybusiness.setJyjg(yycgdMx.getJyjg());
			yybusiness.setCgl(yycgdMx.getCgl());
			yybusiness.setCgje(yycgdMx.getCgje());
			yybusiness.setSysDictInfoByCgzt(yycgdMx.getSysDictInfoCgzt());
			yybusiness.setTjsj(yycgd.getTjsj());
			yybusinessDao.saveOrUpdate(yybusiness);
		}		
		//2.备份到任务最终表
		SysTaskFinis sysTaskFinis = new SysTaskFinis();
		//2.1拷贝属性(任务完成表的id使用任务表的id)
		BeanUtils.copyProperties(sysTask, sysTaskFinis);
		//2.2设置完成时间
		sysTaskFinis.setTaskFinisDate(new Date());	
		sysTaskFinisDao.save(sysTaskFinis);
		//3.从任务临时表删除
		sysTaskDao.delete(sysTask);
		
	}
	/** 
	 * 采购单的发货处理任务
	 * 
	 */
	@Override
	public void saveTask02(SysTask sysTask) {
		//1.1获得任务数据：采购单id转换为整型
		Integer yycgdId = Integer.parseInt(sysTask.getData1());
		//1.2通过id查询采购单
		Yycgd yycgd = yycgdDao.findById(yycgdId);
		List<Yybusiness> list = yybusinessDao.findByCgdId(yycgdId);
		
		//通过药品采购单获取药品明细
		Set<YycgdMx> yycgdMxes = yycgd.getYycgdMxes();
		for (YycgdMx yycgdMx : yycgdMxes) {
			Ypxx ypxx = yycgdMx.getYpxx(); //获取药品信息
			//通过药品信息 ypxx 和 yycgd 作唯一条件去一起去查询数据库 
			Yybusiness yybusiness = yybusinessDao.findByUnique(ypxx,yycgd);	
			SysDictInfo sysDictInfoByCgzt = yycgdMx.getSysDictInfoCgzt();
			
			if (yybusiness!=null){
				yybusiness.setSysDictInfoByCgzt(sysDictInfoByCgzt);
			}

		}				
		//2.备份到任务最终表
		SysTaskFinis sysTaskFinis = new SysTaskFinis();
		//2.1拷贝属性(任务完成表的id使用任务表的id)
		BeanUtils.copyProperties(sysTask, sysTaskFinis);
		//2.2设置完成时间
		sysTaskFinis.setTaskFinisDate(new Date());	
		sysTaskFinisDao.save(sysTaskFinis);
		//3.从任务临时表删除
		sysTaskDao.delete(sysTask);
	}
	/** 采购单入库
	 * 
	 */
	@Override
	public void saveTask03(SysTask sysTask) {
		
		//退货功能
		

	}
	

}
