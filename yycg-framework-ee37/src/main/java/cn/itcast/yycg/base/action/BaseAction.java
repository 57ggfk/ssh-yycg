package cn.itcast.yycg.base.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.yycg.util.FastJsonUtil;

@SuppressWarnings("unchecked")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T t;
	@Override
	public T getModel() {
		return t;
	}
	
	public BaseAction() {
		try {
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType paramType = (ParameterizedType) type;
				Class<T> clazz = (Class<T>) paramType.getActualTypeArguments()[0];
				if (clazz==null) {
					throw new RuntimeException(this+" 使用BaseAction出现异常");
				}
				this.t = clazz.newInstance();//实例化T
			}
			if (t==null) {
				throw new RuntimeException(this+" 使用BaseAction出现异常");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void push(Object o) {
		ActionContext.getContext().getValueStack().push(o);
	}
	
	public void set(String key,Object o) {
		ActionContext.getContext().getValueStack().set(key, o);
	}
	
	public void put(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	public void putSession(String key, Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}
	
	public void putApplication(String key, Object value) {
		ActionContext.getContext().getApplication().put(key, value);
	}
	
	//获取Servlet的API request和response
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	public void write_json(String jsonString) {
		FastJsonUtil.write_json(this.getResponse(), jsonString);
	}
	
	public void write_object(Object object) {
		FastJsonUtil.write_object(this.getResponse(), object);
	}
}
