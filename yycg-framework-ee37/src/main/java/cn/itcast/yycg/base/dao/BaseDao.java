package cn.itcast.yycg.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {
	
	public Serializable save(T t);
	
	public void saveOrUpdate(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
	public T findById(Serializable id);
	
	public List<T> findAll();
	
	public List<T> list(DetachedCriteria dc);
	
	public List<T> list(DetachedCriteria dc, int offset, int limit);
	
	public int getTotalRecord(T t);
	
	public List<T> findAll(T t);
	
	public List<T> findAll(T t, int offset, int limit);
	
}
