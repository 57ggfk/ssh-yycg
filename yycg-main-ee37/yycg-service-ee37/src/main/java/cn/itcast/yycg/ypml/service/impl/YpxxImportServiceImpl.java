package cn.itcast.yycg.ypml.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.util.HxlsOptRowsInterface;
import cn.itcast.yycg.ypml.dao.YpxxDao;
import cn.itcast.yycg.ypml.dao.query.YpxxQuery;

@Service
public class YpxxImportServiceImpl implements HxlsOptRowsInterface {

	@Autowired
	private YpxxDao ypxxDao;
	@Override
	//保存每一行的方法，校验，如果通过保存到这一行到数据库
	public String saveOptRows(int sheetIndex, int curRow, List<String> rowlist) throws Exception {
		
		//A获得数据,数据完整性校验
		//a.1判断是否10列
		if (rowlist.size()!=10) {
			return "数据不完整，必须10列";
		}
		//a.2 //流水号	通用名	剂型	规格	单位	转换系数	中标价格	生产企业	商品名	交易状态(1：正常,2：暂停)
		String bm = rowlist.get(0);		//流水号
		if (StringUtils.isBlank(bm)) {
			return "流水号不能为空";
		}
		String mc = rowlist.get(1);
		if (StringUtils.isBlank(mc)) {
			return "通用名不能为空";
		}
		String jx = rowlist.get(2);
		if (StringUtils.isBlank(bm)) {
			return "剂型不能为空";
		}
		String gg = rowlist.get(3);
		if (StringUtils.isBlank(gg)) {
			return "规格不能为空";
		}
		String dw = rowlist.get(4);
		if (StringUtils.isBlank(dw)) {
			return "单位不能为空";
		}
		String zhxs = rowlist.get(5);
		if (StringUtils.isBlank(zhxs)) {
			return "转换系数不能为空";
		}
		String zbjgStr = rowlist.get(6);
		if (StringUtils.isBlank(bm)) {
			return "中标价格不能为空";
		}
		String scqymc = rowlist.get(7);
		if (StringUtils.isBlank(bm)) {
			return "生产企业不能为空";
		}
		String spmc = rowlist.get(8);
		if (StringUtils.isBlank(bm)) {
			return "商品名称不能为空";
		}
		String jyzt = rowlist.get(9);
		if (StringUtils.isBlank(bm)) {
			return "交易状态不能为空";
		}
		
		//B校验数据唯一性
		//b.1校验流水号的唯一性
		if (StringUtils.isBlank(bm)) {
			return "流水号不能为空";
		}
		if (ypxxDao.findByBm(bm)!=null) {
			return "流水号：["+bm+"]重复";
		}
		
		//b.2校验6个字段的唯一性 SCQYMC, SPMC, MC, JX, GG, ZHXS
		YpxxQuery ypxxQuery = new YpxxQuery();
		ypxxQuery.setMc(mc);
		ypxxQuery.setJx(jx);
		ypxxQuery.setGg(gg);
		ypxxQuery.setZhxs(zhxs);
		ypxxQuery.setScqymc(scqymc);
		ypxxQuery.setSpmc(spmc);
		
		if (ypxxDao.findByUnique(ypxxQuery)!=null) {
			return "流水号为["+bm+"]药品，6个字段重复(通用名,剂型,规格,转换系数,生产企业,商品名)";
		}
		
		//C校验数据合法性
		//c.1 交易状态，数据字典中值是固定的
		SysDictInfo sysDictInfoByJyzt = new SysDictInfo();
		if("1".equals(jyzt)){
			sysDictInfoByJyzt.setDictcode("00301"); //正常交易
		} else if ("2".equals(jyzt)) {
			sysDictInfoByJyzt.setDictcode("00302"); //暂停交易
		} else {
			return "流水号为["+bm+"]药品，交易状态不符合要求，必须为1或2";
		}
		
		//c.2 中标价格
		Float zbjg = 0f;
		try {
			zbjg = Float.valueOf(zbjgStr);
		} catch (Exception e) {
			return "流水号为["+bm+"]药品，中标价格必须为数字";
		}
		//D保存
		Ypxx ypxx = new Ypxx(null, sysDictInfoByJyzt, bm, scqymc, spmc, zbjg, dw, mc, jx, gg, zhxs);
		
		ypxxDao.save(ypxx);
		
		//E返回成功信息
		return SUCCESS;
	}

}
