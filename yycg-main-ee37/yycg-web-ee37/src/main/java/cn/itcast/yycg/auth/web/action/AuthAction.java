package cn.itcast.yycg.auth.web.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.yycg.auth.web.vo.AuthVo;
import cn.itcast.yycg.base.action.BaseAction;
import cn.itcast.yycg.framework.web.result.ResultUtil;

@Controller
@Scope("prototype")
public class AuthAction extends BaseAction<AuthVo> {

	public void login() {
		//----------校验验证码-----------
		
		String inputValidate = getModel().getValidateCode();
		//获取Session中的验证码，可以用ActionContext对象
		String sessionValidate = (String) ActionContext.getContext().getSession().get("validateCode");
		//验证码使用后清除，通过request对象获得session
		ServletActionContext.getRequest().getSession().setAttribute("validateCode", null);
		if (sessionValidate == null) {
			write_object(ResultUtil.createSubmitResult("验证码失效"));
			return;
		}
		if (!inputValidate.equalsIgnoreCase(sessionValidate)) {
			write_object(ResultUtil.createSubmitResult("验证码错误"));
			return;
		}
		
		//准备参数
		String usercode = getModel().getUsercode();
		String pwd = getModel().getPwd();
		
		//登录，通知shiro进行认证
		SecurityUtils.getSubject().login(new UsernamePasswordToken(usercode, pwd));
		//相应提示信息
		write_object(ResultUtil.createSubmitResult("登录成功"));
	}
	
}
