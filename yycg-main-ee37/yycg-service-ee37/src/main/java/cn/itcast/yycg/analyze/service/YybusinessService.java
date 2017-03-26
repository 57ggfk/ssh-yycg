package cn.itcast.yycg.analyze.service;

import java.util.List;
import java.util.Map;

import cn.itcast.yycg.analyze.dao.query.YybusinessQuery;
import cn.itcast.yycg.analyze.pojo.YybusinessQueryCustom;
import cn.itcast.yycg.domain.po.Yybusiness;

public interface YybusinessService {

	public List<Map<String, Object>> findSumByArea(YybusinessQueryCustom yybusinessQueryCustom);

	/**
	 * @param yybusinessQueryCustom
	 * @return
	 */
	public List<Map<String, Object>> findSumByDw(YybusinessQueryCustom yybusinessQueryCustom);

	/**
	 * @param yybusinessQueryCustom
	 * @return
	 */
	Map<String, List<?>> getInfoToEcharts(YybusinessQueryCustom yybusinessQueryCustom);
	
	/**
	 * 查询所有药品信息采购量集合
	 * @param yybusinessQueryCustom
	 * @return
	 */
	public List<Yybusiness> list_drugyzrea(YybusinessQueryCustom yybusinessQueryCustom,Integer firstResult,Integer maxResults);

	/**
	 * @param yybusinessQueryCustom
	 * @return
	 */
	public Integer getTotalRecord(YybusinessQueryCustom yybusinessQueryCustom);

	/**
	 * 
	 * @param yybusinessQueryCustom
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Yybusiness> findBussinessDetail(YybusinessQueryCustom yybusinessQueryCustom,int page,int rows);

	/**
	 * @param yybusinessQueryCustom
	 * @return
	 * 
	 * 信息详情条件查询 总记录数
	 */
	public Integer getTotalRecordWithBussinessDetail(YybusinessQueryCustom YybusinessQueryCustom);

	/**
	 * 根据月份进行查询采购金额
	 * @return
	 */
	public List<Map<String, Object>> findAllByMoth();

	/*
	 * 分页查询总条数
	 */
	public int getTotalRecordByCount(YybusinessQueryCustom yybusinessQueryCustom);



}
