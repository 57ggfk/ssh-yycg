package cn.itcast.yycg.framework.web.result;

import java.util.List;


/**
 * 系统结果工具类
 * @author liangtong
 *
 */
public class ResultUtil {


	/**
	 * 创建错误结果
	 * @param message
	 * @return
	 */
    public static ResultInfo createFail(String message){
    	return new ResultInfo(ResultInfo.TYPE_RESULT_FAIL,message);
    }
    /**
     * 创建敬告提示结果
     * @param message
     * @return
     */
    public static ResultInfo createWarning(String message){
    	return new ResultInfo(ResultInfo.TYPE_RESULT_WARN,message);
    }
    
    /**
     * 创建成功提示结果
     * @param message
     * @return
     */
    public static ResultInfo createSuccess(String message){
    	
    	return new ResultInfo(ResultInfo.TYPE_RESULT_SUCCESS,message);
    }
    
    /**
     * 创建普通信息提示结果
     * @param message
     * @return
     */
    public static ResultInfo createInfo(String message){
    	return new ResultInfo(ResultInfo.TYPE_RESULT_INFO,message);
    }
    
    /**
     * 抛出异常消息
     * @param message
     * @throws ExceptionResultInfo
     */
    public static void throwExcepionResult(String message) throws ExceptionResultInfo{
		throw new ExceptionResultInfo(createFail(message));
	}
    
    /**
	 * 抛出异常消息,含有异常明细
	 * @param message
	 * @param details
	 * @throws ExceptionResultInfo
	 */
	public static void throwExcepionResult(String message,String ... details) throws ExceptionResultInfo{
		ResultInfo resultInfo = createFail(message);
		
		if(details != null){
			for (String detail : details) {
				ResultInfo detailResultInfo = createFail(detail);
				resultInfo.getDetails().add(detailResultInfo);
			}
		}
		throw new ExceptionResultInfo(resultInfo);
	}

	/**
	 * 抛出异常消息,含有异常明细
	 * @param message
	 * @param details
	 * @throws ExceptionResultInfo
	 */
	public static void throwExcepionResult(String message,List<String> details) throws ExceptionResultInfo{
		ResultInfo resultInfo = createFail(message);
		if (details!=null) {
			for (String detail : details) {
				ResultInfo detailResultInfo = createFail(detail);
				resultInfo.getDetails().add(detailResultInfo);
			}
		}
		throw new ExceptionResultInfo(resultInfo);
	}
	/**
	 * 抛出异常(自定义对象)
	 * @param resultInfo
	 * @throws ExceptionResultInfo
	 */
    public static void throwExcepionResult(ResultInfo resultInfo) throws ExceptionResultInfo{
		throw new ExceptionResultInfo(resultInfo);
	}
    /**
     * 抛出异常消息,含有异常明细
     * @param resultInfo
     * @param details
     * @throws ExceptionResultInfo
     */
	public static void throwExcepionResult(ResultInfo resultInfo,List<ResultInfo> details) throws ExceptionResultInfo{
		if(resultInfo != null){
			resultInfo.setDetails(details);
		}
		throw new ExceptionResultInfo(resultInfo);
	}
	
	
	/**
	 * 成功提示信息
	 * @param message
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(String message) {
		return createSubmitResult(createSuccess(message));
	}
	
	/**
	 * 成功提示信息,包括明细信息
	 * @param message
	 * @param details
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(String message,String... details){
		ResultInfo resultInfo = createSuccess(message);
		if(details != null){
			for (String detail : details) {
				resultInfo.getDetails().add(createSuccess(detail));
			}
		}
		return new SubmitResultInfo(resultInfo);
	}
	
	/**
	 * 成功提示信息,包括明细信息
	 * @param message
	 * @param details
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(String message,List<String> details){
		ResultInfo resultInfo = createSuccess(message);
		if(details != null){
			for (String detail:details) {
				resultInfo.getDetails().add(createSuccess(detail));
			}
		}
		return new SubmitResultInfo(resultInfo);
	}
	
	/**
	 * 创建提交结果信息
	 * @param resultInfo
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo){
		return new SubmitResultInfo(resultInfo);
	}
	
	/**
	 * 创建提交结果信息，包括明细信息
	 * @param resultInfo
	 * @param details
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo,List<ResultInfo> details){
		if(resultInfo != null){
			resultInfo.setDetails(details);
		}
		return new SubmitResultInfo(resultInfo);
	}
	
	/**
	 * 创建datagrid结果
	 * @param total
	 * @param rows
	 * @return
	 */
	public static <T> DataGridResultInfo createDataGridResult(long total,List<T> rows){
		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
		dataGridResultInfo.setRows(rows);
		dataGridResultInfo.setTotal(total);
		return dataGridResultInfo;
	}
	
	
	public static void main(String[] args) {

	}
	

}
