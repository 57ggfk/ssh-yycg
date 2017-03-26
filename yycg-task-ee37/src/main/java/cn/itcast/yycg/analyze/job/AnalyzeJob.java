package cn.itcast.yycg.analyze.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.yycg.analyze.service.AnalyzeGetherService;
import cn.itcast.yycg.analyze.service.SysTaskService;
import cn.itcast.yycg.domain.po.SysTask;

//@Component 可以用注解添加到Spring容器中
public class AnalyzeJob {
	
	@Autowired
	private SysTaskService sysTaskService;
	@Autowired
	private AnalyzeGetherService analyzeGetherService;
	private void execute() {
		//top 20
		List<SysTask> list = sysTaskService.findSysTaskTopList(20);
		//System.out.println(list);
		for (SysTask sysTask: list) {
			//获取状态   01 采购单审核通过 02 发货处理 03 入库 04 确认退货
			String taskType = sysTask.getTaskType();		
			if (("01").equals(taskType)) {
				analyzeGetherService.saveTask01(sysTask);
			//发货处理
			}else if (("02").equals(taskType)) {
				analyzeGetherService.saveTask02(sysTask);
			}
			else if (("03").equals(taskType)) {
				analyzeGetherService.saveTask03(sysTask);
			}
			
		}
	}

}
