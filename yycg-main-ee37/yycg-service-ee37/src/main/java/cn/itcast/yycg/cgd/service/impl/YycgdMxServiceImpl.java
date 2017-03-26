package cn.itcast.yycg.cgd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.cgd.dao.YycgdDao;
import cn.itcast.yycg.cgd.dao.YycgdMxDao;
import cn.itcast.yycg.cgd.pojo.YycgdMxQueryCustom;
import cn.itcast.yycg.cgd.service.YycgdMxService;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.domain.po.YycgdMx;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.ypml.dao.YpxxDao;

@Service
public class YycgdMxServiceImpl implements YycgdMxService {

	@Autowired
	private YycgdMxDao yycgdMxDao;
	@Autowired
	private YycgdDao yycgdDao;
	@Autowired
	private YpxxDao ypxxDao;

	@Override
	public int getTotalRecord(YycgdMxQueryCustom yycgdMxQueryCustom) {
		return yycgdMxDao.getTotalRecord(yycgdMxQueryCustom);
	}

	@Override
	public List<YycgdMx> findAll(YycgdMxQueryCustom yycgdMxQueryCustom, int offset, int limit) {
		return yycgdMxDao.findAll(yycgdMxQueryCustom, offset, limit);
	}

	@Override
	public void saveYycgdMxList(Integer yycgdid, String[] ypxxids) {
		// 完整性校验
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		if (yycgd == null) {
			ResultUtil.throwExcepionResult("要操作的采购单已不存在");
		}

		// 状态校验：未提交01001 和 审核未通过01004 可以提交，其它不可以
		String ztId = yycgd.getSysDictInfoByzt().getId();
		if (!("01001".equals(ztId) || "01004".equals(ztId))) {
			ResultUtil.throwExcepionResult("采购单已经提交，无法修改");
		}

		if (ypxxids == null || ypxxids.length < 1) {
			ResultUtil.throwExcepionResult("您没有选择药品");
		}

		// 处理+校验
		for (String ypxxid : ypxxids) {
			// 药品存在
			Ypxx ypxx = ypxxDao.findById(ypxxid);
			if (ypxx == null) {
				ResultUtil.throwExcepionResult("要操作的药品不存在，请刷新列表");
			}

			// 采购单+药品信息必须唯一，如果重复选择，增加数量即可
			YycgdMx yycgdMx = yycgdMxDao.findByUnique(yycgd, ypxx);
			if (yycgdMx != null) {
				ResultUtil.throwExcepionResult("药品[" + ypxx.getBm() + "]重复");
			}

			// 封装明细数据
			yycgdMx = new YycgdMx();

			yycgdMx.setYycgd(yycgd); // 采购单
			yycgdMx.setYpxx(ypxx); // 药品信息
			yycgdMx.setZbjg(ypxx.getZbjg()); // 中标价格
			yycgdMx.setJyjg(ypxx.getZbjg()); // 暂时用中标价格

			yycgdMx.setCgl(0); // 采购量
			yycgdMx.setCgje(0f); // 采购价格

			// * 采购状态 -- 默认值:未受理，开发“采购单受理”，修改明细状态：已发货、无法供货
			yycgdMx.setSysDictInfoCgzt(new SysDictInfo("01101"));

			// 保存
			yycgdMxDao.save(yycgdMx);

		}
	}

	@Override
	public void saveYycgdMxCgls(String[] yycgdmxIds, Integer[] yycgdmxCgls) {

		if (yycgdmxIds == null || yycgdmxCgls == null) {
			ResultUtil.throwExcepionResult("采购量信息为空");
		}
		if (yycgdmxIds.length != yycgdmxCgls.length) {
			ResultUtil.throwExcepionResult("采购量信息错误");
		}
		if (yycgdmxIds.length < 1) {
			ResultUtil.throwExcepionResult("采购量信息为空");
		}

		// 遍历任意一个数组
		for (int i = 0; i < yycgdmxIds.length; i++) {
			YycgdMx yycgdMx = yycgdMxDao.findById(yycgdmxIds[i]);
			if (yycgdMx == null) {
				ResultUtil.throwExcepionResult("采购量信息错误，请刷新列表");
			}
			// 采购量
			Integer cgl = yycgdmxCgls[i];
			yycgdMx.setCgl(cgl);
			// 采购金额=中标价*采购量
			yycgdMx.setCgje(yycgdMx.getZbjg() * cgl);
			// session一级缓存与快照区比较，不一致自动update
			// yycgdMxDao.update(yycgdMx);
		}

	}

	@Override
	public void deleteYycgdMxIds(String[] yycgdmxIds) {
		if (yycgdmxIds == null || yycgdmxIds.length < 1) {
			ResultUtil.throwExcepionResult("删除无效，请刷新列表");
		}
		for (String yycgdmxId : yycgdmxIds) {
			YycgdMx yycgdMx = yycgdMxDao.findById(yycgdmxId);
			if (yycgdMx == null) {
				ResultUtil.throwExcepionResult("要删除的采购单明细已失效，请刷新列表");
			}
			yycgdMxDao.delete(yycgdMx);
		}
	}

	@Override
	/**
	 * 保存药品发货状态
	 */
	public void saveYycgdMxZt(Integer cgdId, String[] yycgdmxIds, String[] yycgdmxztIds) {
		Yycgd yycgd = yycgdDao.findById(cgdId);
		if (yycgd == null) {
			ResultUtil.throwExcepionResult("您要保存药品状态的采购单已不存在");
		}
		// 只有01003 通过审核，才允许修改药品状态
		if ("01005".equals(yycgd.getSysDictInfoByzt().getId())) {
			ResultUtil.throwExcepionResult("已受理的采购单不能进行修改!");
		}
		if (!"01003".equals(yycgd.getSysDictInfoByzt().getId())) {
			ResultUtil.throwExcepionResult("您要保存药品状态的采购单尚未审核!");
		}
		// 循环处理药品状态
		for (int i = 0; i < yycgdmxIds.length; i++) {
			YycgdMx yycgdMx = yycgdMxDao.findById(yycgdmxIds[i]);
			if (yycgdMx == null) {
				ResultUtil.throwExcepionResult("您要保存药品状态明细已不存在");
			}
			yycgdMx.setSysDictInfoCgzt(new SysDictInfo(yycgdmxztIds[i]));

		}
	}
}
