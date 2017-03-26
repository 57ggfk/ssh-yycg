package cn.itcast.yycg.system.service.impl;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.domain.po.SysConf;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.system.dao.DwWsyDao;
import cn.itcast.yycg.system.dao.SysConfDao;
import cn.itcast.yycg.system.service.SysConfService;
@Service
public class SysConfServiceImpl implements SysConfService{
	@Autowired
	private SysConfDao sysConfDao;
	@Autowired
	private DwWsyDao dwWsyDao;
	@Override
	public Integer findCount(SysConf sysConf) {
		return sysConfDao.findCount(sysConf);
	}

	@Override
	public List<SysConf> findAll(SysConf sysConf, Integer firstResult, Integer maxReslults) {
		List<SysConf> all = sysConfDao.findAll(sysConf, firstResult, maxReslults);
		return all;
	}
	
	@Override
	public String getUploadPath(){
		String uploadPath = sysConfDao.findById("00301").getValue();
		try {
			//路径如果不存在就创建
			File file = new File(uploadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			ResultUtil.throwExcepionResult("文件路径有问题，请联系管理员");
		}
		return sysConfDao.findById("00301").getValue();
	}
	@Override
	
	public void updateForValue(String sysid, String[] sysConfId, String[] sysConfValue) {
		//校验
//		DwWsy dwWsy = dwWsyDao.findById(sysid);
//		if(dwWsy==null){
//			ResultUtil.throwExcepionResult("别动,你没资格!");
//		}
		if(sysConfId==null || sysConfValue ==null){
			ResultUtil.throwExcepionResult("哎呀,不行啊,出错了!");
		}
		for (int i = 0; i < sysConfId.length; i++) {
			//获取系统id
			String id = sysConfId[i];
			SysConf sysConf = sysConfDao.findById(id);
			if(sysConf==null){
				ResultUtil.throwExcepionResult("哎呀,出错了,请重来!");
			}
			
			//先将数据存到session中一份
			String value= sysConfValue[i];
			if(value==null){
				ResultUtil.throwExcepionResult("哎呀,出错了,请重来吧!");
			}
			sysConf.setValue(value);
			sysConfDao.update(sysConf);
		}
	}
	
	@Override
	public SysConf findById(String id) {
		return sysConfDao.findById(id);
	}

	@Override
	public void update(String sysConfId, String value) {
		//先查询
		SysConf sysConf = sysConfDao.findById(sysConfId);
		if(sysConf==null){
			ResultUtil.throwExcepionResult("数据异常,刷新重试!");
		}
		sysConf.setValue(value);
		sysConfDao.update(sysConf);
		
	}

}
