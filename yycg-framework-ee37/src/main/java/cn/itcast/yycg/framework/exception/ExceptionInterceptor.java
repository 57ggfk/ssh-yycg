package cn.itcast.yycg.framework.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

import cn.itcast.yycg.framework.web.result.ExceptionResultInfo;
import cn.itcast.yycg.framework.web.result.ResultInfo;
import cn.itcast.yycg.framework.web.result.ResultUtil;
import cn.itcast.yycg.util.FastJsonUtil;

/**
 * 定义一个拦截器，作为系统统一异常处理类
 *
 */
public class ExceptionInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -3570177760250354837L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String result=null;
		try {
			//向后继续执行拦截器，最终拦截器执行完成执行action，最终action返回逻辑视图名赋值给result
			result = actionInvocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			
			//进行异常处理
			//异常流程
			ExceptionResultInfo exceptionResultInfo = null;
			
			//如果系统自定义的异常，直接获取异常信息
			if(e instanceof ExceptionResultInfo){
				exceptionResultInfo  =(ExceptionResultInfo) e;
			} else if(e instanceof UnknownAccountException) {
				ResultInfo resultInfo = ResultUtil.createFail("账户不存在！");
				exceptionResultInfo = new ExceptionResultInfo(resultInfo);
			} else if(e instanceof IncorrectCredentialsException){
				ResultInfo resultInfo = ResultUtil.createFail("密码错误！");
				exceptionResultInfo = new ExceptionResultInfo(resultInfo);
			} else if(e instanceof UnauthorizedException || e instanceof AuthorizationException){
				ResultInfo resultInfo = ResultUtil.createFail("您没有权限！");
				exceptionResultInfo = new ExceptionResultInfo(resultInfo);
			} else{
				//如果运行时程序bug抛出异常，自己构造一个自定义异常
				ResultInfo resultInfo = ResultUtil.createFail("系统未知错误，请与管理员联系！");
				exceptionResultInfo = new ExceptionResultInfo(resultInfo);
			}
			
			
			//根据http的头信息判断是否是ajax请求，如果头信息中有X-Requested-With:XMLHttpRequest说明是ajax请求
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
				//如果是当前请求是ajax请求，将异常信息转json输出
				String jsonString = FastJsonUtil.toJSONString(exceptionResultInfo);
				HttpServletResponse response = ServletActionContext.getResponse();
				FastJsonUtil.write_json(response, jsonString);
				
			}else{
				//如果当前不是ajax请求，将异常信息通过error.jsp展示给用户
				//获取值栈对象
				ValueStack valueStack = ServletActionContext.getValueStack(request);
				//将异常信息放到值栈的栈顶
				valueStack.push(exceptionResultInfo);
				//将逻辑视图名改为error_jsp，在framework工程的struts-base.xml中定义全局result中有定义
				result = "error_jsp";
			}
			
		}
		
		return result;
	}

}
