package cn.itcast.yycg.base.dao.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.yycg.base.dao.BaseDao;

@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	@Autowired
	public void setHT(HibernateTemplate hibernateTemplate) {
		super.setHibernateTemplate(hibernateTemplate);
	}
	
	/**
	 * 根据传过来的Query类，封装DetachedCriteria条件
	 * @param t
	 * @return 
	 */
	protected DetachedCriteria getDetachedCriteria(T t) {
		return DetachedCriteria.forClass(beanClass);
	}
	
	private Class<T> beanClass; //T的实际类型
	
	//在构造方法内获取T的实际类型
	public BaseDaoImpl() {
		try {
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {//重点，以前没有关系用过
				ParameterizedType paramType = (ParameterizedType) type;
				beanClass = (Class<T>) paramType.getActualTypeArguments()[0]; //最终得到T的实际类型
			}
			if (beanClass==null) {
				throw new RuntimeException(this+" 是用BaseDao异常");  //重点，这用用法不熟悉
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public Serializable save(T entity) {
		return this.getHibernateTemplate().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	
	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(beanClass, id);
	}

	@Override
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().find("from "+beanClass.getName());
	}

	@Override
	public List<T> list(DetachedCriteria dc) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
	}

	@Override
	public List<T> list(DetachedCriteria dc, int offset, int limit) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, offset, limit);
	}
	
	@Override
	public int getTotalRecord(T	t) {
		DetachedCriteria dc = this.getDetachedCriteria(t);  
		dc.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(dc);
		if (list!=null && list.size()>0) {
			return list.get(0).intValue();
		}
		return 0;
	}
	
	@Override
	public List<T> findAll(T t) {
		DetachedCriteria dc = this.getDetachedCriteria(t); 
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
	}

	@Override
	public List<T> findAll(T t, int offset, int limit) {
		DetachedCriteria dc = this.getDetachedCriteria(t); 
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, offset, limit);
	}
	
	/**
	 * 遍历实体对象的属性，把值为空白字符串的属性设置为null，非空白字符串trim一下
	 */
	protected void Blank2Null(Object entity) {
		try { 
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(entity); 
			
			for (int i = 0; i < descriptors.length; i++) { 
				
				String name = descriptors[i].getName(); //属性名称
				
				if (!StringUtils.equals(name, "class")) { ///判断如果不是class
					
					Object value = propertyUtilsBean.getNestedProperty(entity, name); //获取该属性的值
					
					if (value instanceof String) {//如果这个值是String
						String valueStr = (String)value;
						if (StringUtils.isBlank(valueStr)) {//如果属性值为空串
							propertyUtilsBean.setNestedProperty(entity, name, null);//设置属性值为null
						} else {
							propertyUtilsBean.setNestedProperty(entity, name, valueStr.trim());//设置属性值为null
						}
					}
					
				} 
			} 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	}
	
	/**
	 * 获得Example把实体对象封装为条件，同时把实体对象的值为空白字符串的属性设置为null，文本类型使用ilike模糊匹配
	 */
	protected Example getExample(Object entiry) {
		if (entiry==null) {
			return null;
		}
		Blank2Null(entiry);
		return Example.create(entiry).enableLike(MatchMode.ANYWHERE).ignoreCase().excludeZeroes();
	}
	
}

