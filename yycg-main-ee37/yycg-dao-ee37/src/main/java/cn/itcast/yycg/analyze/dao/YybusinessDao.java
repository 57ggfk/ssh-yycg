package cn.itcast.yycg.analyze.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.yycg.analyze.dao.query.YybusinessQuery;
import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yybusiness;
import cn.itcast.yycg.domain.po.Yycgd;

public interface YybusinessDao extends BaseDao<Yybusiness> {
	/**
	 * map<areaname,HH镇>
	 * map<zje,100>
	 * @return
	 */
	public List<Map<String, Object>> findSumByArea(YybusinessQuery yybusinessQuery);

	/**
	 * 通过单位查询 对应单位下面的卫生院的详细信息
	 * @param yybusinessQueryCustom
	 * @return
	 */
	public List<Map<String, Object>> findSumByDw(YybusinessQuery yybusinessQuery);


	/**
	 * 查询所有药品采购信息
	 * @param yybusinessQueryCustom
	 * @return 所有药品采购量以及采购金额的集合
	 * 需要的字段: 流水号,通用名称,采购量,采购金额
	 */
	public List<Map<String, Object>> list_drugyzrea(YybusinessQuery yybusinessQuery,Integer firstResult,Integer maxResults);


	/**
	 * @param yycgdId
	 * 
	 * 通过采购单id查询所有信息
	 * @return 
	 * 
	 */
	public List<Yybusiness> findByCgdId(Integer yycgdId);


	/**
	 * @param yybusinessQueryCustom
	 * @return
	 */
	public Integer getTotalRecordWithBussinessDetail(YybusinessQuery yybusinessQuery);

	/**
	 * @param yybusinessQueryCustom
	 * @param pageQuery_star
	 * @param pageQuery_pageSize
	 * @return
	 */
	public List<Yybusiness> findAllWithBussinessDetail(YybusinessQuery yybusinessQuery, int pageQuery_star,
			int pageQuery_pageSize);

	/**
	 * @param yybusinessQuery
	 * @return
	 */
	DetachedCriteria getDetachedCriteriaWithBussinessDetail(YybusinessQuery yybusinessQuery);


	/**
	 * 查询总记录数
	 * @param yybusinessQueryCustom
	 * @return
	 */
	public int getTotalRecordByCount(YybusinessQuery yybusinessQuery);


	/**通过药品信息 和药品采购单 唯一查询
	 * @param ypxx
	 * @param yycgd
	 * @return 
	 */
	public Yybusiness findByUnique(Ypxx ypxx, Yycgd yycgd);

	
	/**
	 * 按照月份统计销售金额
	 * @return
	 */
	public List<Map<String, Object>> findAllByMonth();



}
