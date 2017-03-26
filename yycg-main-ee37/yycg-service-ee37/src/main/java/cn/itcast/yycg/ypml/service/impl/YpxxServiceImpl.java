package cn.itcast.yycg.ypml.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mchange.v1.db.sql.ResultSetUtils;

import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.province.service.YpxxSheng;
import cn.itcast.yycg.ypml.dao.YpxxDao;
import cn.itcast.yycg.ypml.dao.query.YpxxQuery;
import cn.itcast.yycg.ypml.pojo.YpxxQueryCustom;
import cn.itcast.yycg.ypml.service.YpxxService;

@Service
public class YpxxServiceImpl implements YpxxService {

	@Autowired
	private YpxxDao ypxxDao;
	@Override
	public List<Ypxx> findAll(YpxxQueryCustom ypxxQueryCustom) {
		
		YpxxQuery ypxxQuery = new YpxxQuery();
		BeanUtils.copyProperties(ypxxQueryCustom, ypxxQuery);
		List<Ypxx> list = ypxxDao.findAll(ypxxQuery);
		return list;
	}
	@Override
	public int saveSyncypxx(List<YpxxSheng> allYpxxSheng) {
		int s = 0; //记录成功条数
		for (YpxxSheng ypxxSheng : allYpxxSheng) {
			//校验流水号是否重复
			if (ypxxDao.findByBm(ypxxSheng.getBm())!=null) {
				System.err.println("流水号重复");
				continue; //忽略
			}
			
			//校验6个字段是否重复
			YpxxQuery ypxxQuery = new YpxxQuery();
			ypxxQuery.setMc(ypxxSheng.getMc());
			ypxxQuery.setJx(ypxxSheng.getJx());
			ypxxQuery.setGg(ypxxSheng.getGg());
			ypxxQuery.setZhxs(ypxxSheng.getZhxs());
			ypxxQuery.setScqymc(ypxxSheng.getScqymc());
			ypxxQuery.setSpmc(ypxxSheng.getSpmc());
			if (ypxxDao.findByUnique(ypxxQuery) != null) {
				System.err.println("6字段重复重复");
				continue; //忽略
			}
			
			//封装数据
			Ypxx ypxx = new Ypxx();
			BeanUtils.copyProperties(ypxxSheng, ypxx);
			
			//交易状态
			SysDictInfo sysDictInfo =   new SysDictInfo();
			if ("1".equals(ypxxSheng.getJyzt())) {
				//正常交易
				sysDictInfo.setId("00301");
			} else if ("2".equals(ypxxSheng.getJyzt())) {
				//暂停交易
				sysDictInfo.setId("00302");
			}
			 
			ypxx.setSysDictInfoByJyzt(sysDictInfo);
			try {
				//保存
				ypxxDao.save(ypxx);
				//记录成功次数
				s++;
			} catch (Exception e) {
				ResultUtil.throwExcepionResult(e.getMessage());
			}
		}
		
		return s;
	}
	@Override
	public int getTotalRecord(YpxxQueryCustom ypxxQueryCustom) {
		return ypxxDao.getTotalRecord(getYpxxQuery(ypxxQueryCustom));
	}
	@Override
	public List<Ypxx> findAll(YpxxQueryCustom ypxxQueryCustom, int offset, int limit) {
		return ypxxDao.findAll(getYpxxQuery(ypxxQueryCustom), offset, limit);
	}
	
	//封装条件，用于条件分页查询
	private YpxxQuery getYpxxQuery(YpxxQueryCustom ypxxQueryCustom) {
		YpxxQuery ypxxQuery = new YpxxQuery();
		if (ypxxQueryCustom!=null) {
			BeanUtils.copyProperties(ypxxQueryCustom, ypxxQuery);
		}
		return ypxxQuery;
	}
}
