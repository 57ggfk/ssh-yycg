package cn.itcast.yycg.system.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.SysDictInfo;
import cn.itcast.yycg.system.dao.SysDictInfoDao;

@Repository
public class SysDictInfoDaoImpl extends BaseDaoImpl<SysDictInfo> implements SysDictInfoDao {

	/**
	 * 分页查询条件使用
	 * */
	@Override
	public DetachedCriteria getDetachedCriteria(SysDictInfo t) {
		//封装条件，面向表：通过typecode查询SYS_DICT_INFO
		//封装条件，面向对象：通过sysDictType的id查询sysDictInfo
//		String typecode = t.getSysDictType().getTypecode();
//		return super.getDetachedCriteria(t).add(Restrictions.eq("sysDictType.id", typecode));
		//封装条件，面向对象2：通过sysDictType查询sysDictInfo，底层比较的是sysDictType的id;
		return super.getDetachedCriteria(t).add(Restrictions.eq("sysDictType", t.getSysDictType()));
	}

	/**
	 * 通过dictCode查询对应的信息
	 * 
	 */
	@Override
	public List<SysDictInfo> findByDictCode(String dictCode) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysDictInfo.class);
		detachedCriteria.add(Restrictions.eq("dictcode",dictCode).ignoreCase());
		return (List<SysDictInfo>) this.getHibernateTemplate().findByCriteria(detachedCriteria);

	}

	/** 
	 * 通过TypeCode查询对应的信息
	 */
	@Override
	public List<SysDictInfo> findByTypeCode(String typecode) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysDictInfo.class);
		//创建子查询条件
		DetachedCriteria dc = detachedCriteria.createCriteria("sysDictType");
		//为子查询添加条件
		dc.add(Restrictions.eq("typecode", typecode));		
		return (List<SysDictInfo>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}


}
