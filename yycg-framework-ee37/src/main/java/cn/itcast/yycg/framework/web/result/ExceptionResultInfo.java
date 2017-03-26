package cn.itcast.yycg.framework.web.result;


/**
 * 自定义系统异常类
 * 实际开发自定义异常应该继承Exception，如果异常Exception对代码有侵入性
 */
public class ExceptionResultInfo extends RuntimeException implements ProcessResult {

	// 系统统一使用的结果类，包括了 提示信息类型和信息内容
	private ResultInfo resultInfo;

	public ExceptionResultInfo(ResultInfo resultInfo) {
		super(resultInfo.getMessage());
		this.resultInfo = resultInfo;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

}
