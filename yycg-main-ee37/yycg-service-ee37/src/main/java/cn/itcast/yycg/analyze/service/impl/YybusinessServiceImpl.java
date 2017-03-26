package cn.itcast.yycg.analyze.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ddf.EscherSerializationListener;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.analyze.dao.YybusinessDao;
import cn.itcast.yycg.analyze.dao.query.YybusinessQuery;
import cn.itcast.yycg.analyze.pojo.YybusinessQueryCustom;
import cn.itcast.yycg.analyze.service.YybusinessService;
import cn.itcast.yycg.auth.pojo.ActiveUser;
import cn.itcast.yycg.base.pojo.PageParameter;
import cn.itcast.yycg.domain.po.DwWsy;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yybusiness;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.system.dao.DwWsyDao;
import oracle.sql.NUMBER;

@Service
public class YybusinessServiceImpl implements YybusinessService {

	@Autowired
	private YybusinessDao yybusinessDao;
	@Resource
	// 单位卫生院Dao
	private DwWsyDao dwWsyDao;

	public List<Map<String, Object>> findSumByArea(YybusinessQueryCustom yybusinessQueryCustom) {
		List<Map<String, Object>> findSumByArea = yybusinessDao.findSumByArea(yybusinessQueryCustom);
		return findSumByArea;
	}

	/*
	 * 通过activerAuser 获取到用户 ,通过用户的id查询到对应的单位,通过单位得到单位对应的id
	 *
	 */
	@Override
	public List<Map<String, Object>> findSumByDw(YybusinessQueryCustom yybusinessQueryCustom) {

		// 防止空指针异常
		if (yybusinessQueryCustom == null) {
			yybusinessQueryCustom = new YybusinessQueryCustom();
			yybusinessQueryCustom.setStartDate("");
			yybusinessQueryCustom.setEndDate("");
		}

		// 在shiro中获得用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();

		// 获取用的分组信息 判断是供应商还是卫生院还是,卫生室
		String groupid = activeUser.getGroupid();

		if (groupid.equals("s0101") || groupid.equals("s0102")) {

			// 通过id查询到对应的单位信息
			DwWsy dwWsy = dwWsyDao.findById(activeUser.getSysid());
			// 获取到单位的信息 ,然后通过单位信息获取到地区对应的id
			String sysAreaId = dwWsy.getSysArea().getId();
			yybusinessQueryCustom.setSysAreaId(sysAreaId);

		} else {
			ResultUtil.throwExcepionResult("对不起,你没有访问权限");
		}
		// 调用Dao层进行查询
		return yybusinessDao.findSumByDw(yybusinessQueryCustom);
	}

	/**
	 * 将卫生院查询的统计信息转化成 Echar需要的数据格式 yybusinessQueryCustom 为查询条件 ( 只能输入日期 日期格式为字符串
	 * 如 2016-12-20) map.getkey("xList") ; 返回的是卫生室名称 map.getkey("yList") ;
	 * 返回的是卫生室采购金额
	 */
	@Override
	public Map<String, List<?>> getInfoToEcharts(YybusinessQueryCustom yybusinessQueryCustom) {
		// 查询卫生院对应卫生室的 采购数据信息
		List<Map<String, Object>> list = findSumByDw(yybusinessQueryCustom);

		// 定义x轴数据
		List<String> xList = new ArrayList<>();
		// 定义y轴数据
		List<Double> yList = new ArrayList<>();

		for (Map<String, Object> map : list) {
			String areaName = (String) map.get("AREANAME");
			Number zje = (Number) map.get("ZJE");
			xList.add(areaName);
			yList.add(zje.doubleValue());
		}
		// 将查询结果重新封装成Echar需要的格式
		Map<String, List<?>> resultMap = new HashMap<>();

		resultMap.put("xList", xList);
		resultMap.put("yList", yList);

		return resultMap;
	}

	@Override
	// 查询所有药品采购详情统计
	public List<Yybusiness> list_drugyzrea(YybusinessQueryCustom yybusinessQueryCustom, Integer firstResult,
			Integer maxResults) {
		// 防止空指针异常
		if (yybusinessQueryCustom == null) {
			yybusinessQueryCustom = new YybusinessQueryCustom();
			yybusinessQueryCustom.setStartDate("");
			yybusinessQueryCustom.setEndDate("");
		}
		// 处理分页
		// 在shiro中获得用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();

		// 获取用的分组信息 判断是供应商还是卫生院还是,卫生室
		String groupid = activeUser.getGroupid();

		if (yybusinessQueryCustom != null && (groupid.equals("s0101") || groupid.equals("s0102"))) {

			// 通过id查询到对应的单位信息
			DwWsy dwWsy = dwWsyDao.findById(activeUser.getSysid());
			// 获取到单位的信息 ,然后通过单位信息获取到地区对应的id
			String sysWsyId = dwWsy.getId();
			yybusinessQueryCustom.getDwWsy().setId(sysWsyId);

		} else {
			ResultUtil.throwExcepionResult("对不起,你没有访问权限");
		}
		// 此map是从数据库查询出来的值,在Service层转换为对象后返回Action
		List<Map<String, Object>> reslutMap = yybusinessDao.list_drugyzrea(yybusinessQueryCustom, firstResult,
				maxResults);

		List<Yybusiness> reslutList = new ArrayList<>();
		for (Map<String, Object> map : reslutMap) {

			// 处理
			Yybusiness yybusiness = new Yybusiness();
			// 流水号
			Ypxx ypxx = new Ypxx();
			ypxx.setBm((String) map.get("BM"));
			// 药品名称
			ypxx.setMc((String) map.get("MC"));
			yybusiness.setYpxx(ypxx);
			// 采购量
			Number cgl = (Number) map.get("CGL");
			yybusiness.setCgl(cgl.intValue());
			// 采购金额
			Number cgje = (Number) map.get("CGJE");
			yybusiness.setCgje(cgje.floatValue());
			reslutList.add(yybusiness);
		}
		return reslutList;
	}

	/**
	 * 通过条件查询 订单详情 需要区分不同的人员,返回的查询结果不相同
	 * 
	 */
	@Override
	public List<Yybusiness> findBussinessDetail(YybusinessQueryCustom yybusinessQueryCustom, int page, int rows) {

		// 获取用户判断身份
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		// 获取用户的信息
		// 获取用的分组信息 判断是供应商还是卫生院还是卫生室
		String groupid = activeUser.getGroupid();
		if (groupid.equals("s0101") || groupid.equals("s0102")) {
			yybusinessQueryCustom.setDw("dwWsy"); // 单位卫生院
			yybusinessQueryCustom.setDwId(activeUser.getSysid());
		} else if (groupid.equals("s0103")) {
			yybusinessQueryCustom.setDw("dwWss"); // 单位卫生室
			yybusinessQueryCustom.setDwId(activeUser.getSysid());

		} else if (groupid.equals("s0104")) {
			yybusinessQueryCustom.setDw("dwGys"); // 单位供应商
			yybusinessQueryCustom.setDwId(activeUser.getSysid());
		}
		// 获取总数
		Integer totalCount = yybusinessDao.getTotalRecordWithBussinessDetail(yybusinessQueryCustom);
		// 处理分页信息
		PageParameter pageParameter = new PageParameter(page, rows, totalCount);
		// 返回查询结果
		List<Yybusiness> list = yybusinessDao.findAllWithBussinessDetail(yybusinessQueryCustom,
				pageParameter.getPageQuery_star(), pageParameter.getPageQuery_pageSize());
		return list;
	}

	/**
	 * 通过条件查询所有记录(分页条件)
	 * 
	 */
	@Override
	public Integer getTotalRecord(YybusinessQueryCustom yybusinessQueryCustom) {
		return yybusinessDao.getTotalRecord(yybusinessQueryCustom);
	}

	/**
	 * 统计查询交易详情(分页条件)
	 * 
	 */
	@Override
	public Integer getTotalRecordWithBussinessDetail(YybusinessQueryCustom yybusinessQueryCustom) {
		return yybusinessDao.getTotalRecordWithBussinessDetail(yybusinessQueryCustom);
	}

	@Override
	// 该层给出相应的意见和建议
	public List<Map<String, Object>> findAllByMoth() {
		List<Map<String, Object>> list = yybusinessDao.findAllByMonth();
		return list;
	}

	@Override
	public int getTotalRecordByCount(YybusinessQueryCustom yybusinessQueryCustom) {
		// 防止空指针异常
		if (yybusinessQueryCustom == null) {
			yybusinessQueryCustom = new YybusinessQueryCustom();
			yybusinessQueryCustom.setStartDate("");
			yybusinessQueryCustom.setEndDate("");
		}
		// 在shiro中获得用户
		ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
		// 获取用的分组信息 判断是供应商还是卫生院还是,卫生室
		String groupid = activeUser.getGroupid();
		// TODO 这里的query会有null问题
		if (groupid.equals("s0101") || groupid.equals("s0102")) {

			// 通过id查询到对应的单位信息
			DwWsy dwWsy = dwWsyDao.findById(activeUser.getSysid());
			// 获取到单位的信息 ,然后通过单位信息获取到地区对应的id
			String sysWsyId = dwWsy.getId();
			yybusinessQueryCustom.getDwWsy().setId(sysWsyId);

		} else {
			ResultUtil.throwExcepionResult("对不起,你没有访问权限");
		}
		return yybusinessDao.getTotalRecordByCount(yybusinessQueryCustom);
	}

}
