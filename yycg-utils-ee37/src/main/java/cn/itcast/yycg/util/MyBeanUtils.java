package cn.itcast.yycg.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class MyBeanUtils {
	
	/**
	 * 
	 * @param dest 目标（Destination）
	 * @param orig 源（Origin）
	 */
	public static void copyProperties(Object dest , Object orig){
		try {
			dateConvert();
			
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 目标对象设置Class，使用反射实例化
	 * @param destClass
	 * @param orig
	 */
	public static <T> void copyProperties(Class<T> destClass , T orig){
		try {
			T dest = destClass.newInstance();
			
			copyProperties(dest, orig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	/**
	 * 封装数据
	 * @param bean
	 * @param parameterMap
	 */
	public static void populate(Object bean, Map<String, String[]> properties) {
		try {
			//1 注册转换器
			dateConvert();
			
			//2 封装
			BeanUtils.populate(bean, properties);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	
	/**
	 * 高级 ： 反射 + 泛型
	 * 解析： static <T> T populate2(Class<T>
	 * 		第一个 <T> 表示声明，及定义
	 * 		第二个 T 表示 返回的类型
	 * 		第三个 Class<T> 表示接受实际类型
	 * @param beanClass
	 * @param properties
	 * @return
	 */
	public static <T> T populate(Class<T> beanClass, Map<String, String[]> properties) {
		try {
			//1 使用反射创建实例
			T bean = beanClass.newInstance();	//等效  User user = new User();
			
			//2 使用上面的方法封装
			populate(bean, properties);
			//3 将封装后的结果返回
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	//时间转换器
	private static void dateConvert(){
		ConvertUtils.register(new Converter() {
			@Override
			@SuppressWarnings("rawtypes")
			public Object convert(Class type, Object value) {
				if(value == null){
					return null;
				}
				if(type == java.util.Date.class){
					// yyyy-MM-dd
					try {
						return DateUtil.parseDate((String)value);
					} catch (Exception e) {
						//yyyy-MM-dd HH:mm:ss
						try {
							return DateUtil.parseDateTime((String)value);
						} catch (Exception e2) {
							//HH:mm:ss
							return DateUtil.parseTime((String)value);
						}
					}
				}
				return null;
			}
		}, java.util.Date.class);
	}

}
