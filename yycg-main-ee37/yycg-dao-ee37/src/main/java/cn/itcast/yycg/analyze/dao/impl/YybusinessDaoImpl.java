package cn.itcast.yycg.analyze.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.sql.JoinType;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.ctc.wstx.util.StringUtil;

import cn.itcast.yycg.analyze.dao.YybusinessDao;
import cn.itcast.yycg.analyze.dao.query.YybusinessQuery;
import cn.itcast.yycg.base.dao.impl.BaseDaoImpl;
import cn.itcast.yycg.domain.po.DwWsy;
import cn.itcast.yycg.domain.po.Ypxx;
import cn.itcast.yycg.domain.po.Yybusiness;
import cn.itcast.yycg.domain.po.Yycgd;
import cn.itcast.yycg.system.dao.DwWsyDao;
import cn.itcast.yycg.util.MyUtil;

@Repository
public class YybusinessDaoImpl extends BaseDaoImpl<Yybusiness> implements YybusinessDao {

	@Resource
	private DwWsyDao dwWsyDo;

	@Override
	// 少参数YybusinessQueryCustom
	public List<Map<String, Object>> findSumByArea(YybusinessQuery yybusinessQuery) {

		// 1 获得Hibernate的Session
		Session session = this.getSessionFactory().getCurrentSession();

		// 2 拼凑sql语句
		StringBuilder sb = new StringBuilder("");
		sb.append("select t.areaname AREANAME, sum(t.cgje) SUM");
		sb.append("  from (select za.areaname, cx.cgje");
		sb.append("          from (select yb.cgje, sa.parentid");
		sb.append("                  from yybusiness yb, dw_wss ws, sys_area sa");
		sb.append("                 where yb.wss_id = ws.id");
		// 2.1 获得条件
		if (yybusinessQuery != null) {

		}
		sb.append("                   and ws.dq = sa.id) cx,");
		sb.append("               sys_area za");
		sb.append("         where cx.parentid = za.id) t");
		sb.append("  group by t.areaname");

		// 3 获得sql语句处理对象
		SQLQuery sqlQuery = session.createSQLQuery(sb.toString());
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));

		// 4 设置封装对象(默认封装到JavaBean中)，封装到map中
		List list = sqlQuery.list();
		return list;
	}

	/**
	 * @author jiang 查询卫生院对应的卫生室采购明细
	 */
	@Override

	public List<Map<String, Object>> findSumByDw(YybusinessQuery yybusinessQuery) {

		// 1 获得hibernate session
		Session session = this.getSessionFactory().getCurrentSession();

		// 2 拼凑sql语句
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select t.areaname,sum(t.cgje) zje from ( ");
		sqlBuilder.append("  select za.areaname , cx.cgje from (  ");
		sqlBuilder.append("      select yb.cgje,sa.id ");
		sqlBuilder.append("        from yybusiness yb ,dw_wss ws ,sys_area sa  ");
		sqlBuilder.append("          where yb.wss_id = ws.id and ws.dq = sa.id ");

		// ---------------------------只统计已发货的
		// 及订单受理完成的---------------------------------------
		sqlBuilder.append(" and yb.cgzt = '01102' ");
		// ---------------------------------------------------------------------
		// 拼凑时间条件, 有四种情况 开始时间输入,结束时间未输入;开始时间未输入,结束时间输入 ;两个都输入;两个都没有输入.
		if (!yybusinessQuery.getStartDate().equals("")) {
			// 如果两个都输入了
			if (!yybusinessQuery.getEndDate().equals("")) {
				sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') between '" + yybusinessQuery.getStartDate()
						+ "'  and   '" + yybusinessQuery.getEndDate() + "'   ");
			} else {// 说明没有输入结束时间
				sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') >= '" + yybusinessQuery.getStartDate() + "'");
			}
		} else {// 说明 没有输入开始时间
				// 如果输入了结束时间
			if (!yybusinessQuery.getEndDate().equals("")) {
				sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') <= '" + yybusinessQuery.getEndDate() + "'");
			}

		}

		sqlBuilder.append("   ) cx  , sys_area za  ");
		sqlBuilder.append("      where cx.id = za.id   ");
		// ---------------------------只统计已发货的
		// 通过地区id进行模糊匹配 例如 1.13.9. 从而查询到该镇下面所对应的卫生室
		if (!yybusinessQuery.getSysAreaId().equals("")) {
			sqlBuilder.append(" and za.id  like '" + yybusinessQuery.getSysAreaId() + "%' ");
		}

		sqlBuilder.append("   ) t group by t.areaname ");
		sqlBuilder.append("");
		String sql = sqlBuilder.toString();

		// 3 获得处理对象
		SQLQuery sqlQuery = session.createSQLQuery(sql);

		// 4 设置封装对象 （默认情况封装JavaBean中，希望到Map）
		sqlQuery.setResultTransformer(Transformers.aliasToBean(HashMap.class));

		return sqlQuery.list();
	}

	@Override
	// 查询所有药品采购信息
	public List<Map<String, Object>> list_drugyzrea(YybusinessQuery yybusinessQuery, Integer firstResult,
			Integer maxResults) {

		Session session = this.getSessionFactory().getCurrentSession();
		StringBuilder sqlBuilder = new StringBuilder();

		sqlBuilder.append("select xx.BM,xx.MC,sum(xx.cgl) cgl,sum(xx.cgje) cgje   ");
		sqlBuilder.append("				from (select yy.*,yx.*  ");
		sqlBuilder.append("							from YYBUSINESS yy,YPXX yx,(select ws.id ");
		sqlBuilder.append(
				"																					from DW_WSS ws,(select * from DW_WSY wy   ");
		if (yybusinessQuery.getDwWsy() != null) {
			sqlBuilder
					.append("																																	 where id = '"
							+ yybusinessQuery.getDwWsy().getId() + "'   ");
		}

		sqlBuilder.append("																					) za  ");
		sqlBuilder.append(
				"																					where ws.dq like za.dq||'%') wss ");
		sqlBuilder.append("							where yy.WSS_ID in(wss.id)  ");
		sqlBuilder.append("							and YX.ID = YY.YPXX_ID  ");
		// 及订单受理完成的---------------------------------------
		sqlBuilder.append(" and yy.cgzt = '01102' ");
		// ---------------------------------------------------------------------
		sqlBuilder.append("							) xx  ");
		sqlBuilder.append("   where 1=1  ");

		// 拼凑时间条件, 有四种情况 开始时间输入,结束时间未输入;开始时间未输入,结束时间输入 ;两个都输入;两个都没有输入.
		if (yybusinessQuery != null) {
			if (yybusinessQuery.getStartDate() != null && !yybusinessQuery.getStartDate().equals("")) {
				// 如果两个都输入了
				if (!yybusinessQuery.getEndDate().equals("")) {
					sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') between '" + yybusinessQuery.getStartDate()
							+ "'  and   '" + yybusinessQuery.getEndDate() + "'   ");
				} else {// 说明没有输入结束时间
					sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') >= '" + yybusinessQuery.getStartDate() + "'");
				}
			} else {// 说明 没有输入开始时间
					// 如果输入了结束时间
				if (yybusinessQuery.getEndDate() != null && !yybusinessQuery.getEndDate().equals("")) {
					sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') <= '" + yybusinessQuery.getEndDate() + "'");
				}

			}

		}
		sqlBuilder.append("    GROUP BY xx.bm,xx.MC order by cgje desc  ");

		List<Map<String, Object>> list = session.createSQLQuery(sqlBuilder.toString()).setFirstResult(firstResult)
				.setMaxResults(maxResults).setResultTransformer(Transformers.aliasToBean(HashMap.class)).list();

		DetachedCriteria dc = DetachedCriteria.forClass(Yybusiness.class);
		return list;
	}

	@Override
	public int getTotalRecordByCount(YybusinessQuery yybusinessQuery) {
		/*
		 * //DetachedCriteria dc = this.getDetachedCriteria(yybusinessQuery);
		 * StringBuilder sqlBuilder = new StringBuilder(); sqlBuilder.append(
		 * "select count(1) from (select YP.BM"); sqlBuilder.append(
		 * "					from YYBUSINESS y,YPXX yp  ");
		 * sqlBuilder.append("					WHERE y.YPXX_ID = yp.ID"); if
		 * (yybusinessQuery!=null) { if
		 * (yybusinessQuery.getStartDate()!=null&&!yybusinessQuery.getStartDate(
		 * ).equals("")) { // 如果两个都输入了 if
		 * (!yybusinessQuery.getEndDate().equals("")) { sqlBuilder.append(
		 * "  and to_char(tjsj,'yyyy-mm-dd') between '" +
		 * yybusinessQuery.getStartDate() + "'  and   '" +
		 * yybusinessQuery.getEndDate() + "'   "); } else {// 说明没有输入结束时间
		 * sqlBuilder.append("  and to_char(tjsj,'yyyy-mm-dd') >= '" +
		 * yybusinessQuery.getStartDate() + "'"); } } else {// 说明 没有输入开始时间 //
		 * 如果输入了结束时间 if
		 * (yybusinessQuery.getEndDate()!=null&&!yybusinessQuery.getEndDate().
		 * equals("")) { sqlBuilder.append(
		 * "  and to_char(tjsj,'yyyy-mm-dd') <= '" +
		 * yybusinessQuery.getEndDate() + "' "); }
		 * 
		 * } } sqlBuilder.append(" GROUP BY YP.BM)"); Session session =
		 * this.getSessionFactory().getCurrentSession(); List<Number> list =
		 * session.createSQLQuery(sqlBuilder.toString()).list(); if (list!=null
		 * && list.size()>0) { return list.get(0).intValue(); } return 0;
		 */
		List<Map<String, Object>> list = list_drugyzrea(yybusinessQuery, 0, 99999999);
		return list.size();
	}

	/*
	 * 通过采购单id查询 yybussiness 信息
	 */
	@Override
	public List<Yybusiness> findByCgdId(Integer yycgdId) {

		// 获得hibernate session
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Yybusiness.class);
		detachedCriteria.add(Restrictions.eq("yycgd.id", yycgdId));
		List<Yybusiness> list = (List<Yybusiness>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return list;

	}

	// 数据统计的离线条件查询
	@Override
	public DetachedCriteria getDetachedCriteriaWithBussinessDetail(YybusinessQuery yybusinessQuery) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Yybusiness.class);
		// 创建子查询
		DetachedCriteria dwWssDc = detachedCriteria.createCriteria("dwWss");// 单位卫生室
		DetachedCriteria dwGysDc = detachedCriteria.createCriteria("dwGys");// 单位供应商
		DetachedCriteria yycgdDc = detachedCriteria.createCriteria("yycgd");// 医药采购单
		DetachedCriteria ypxxDc = detachedCriteria.createCriteria("ypxx");// 药品信息
		// DetachedCriteria sysDictInfoByCgztDc =
		// detachedCriteria.createCriteria("sysDictInfoByCgzt");//采购状态
		// 创建子查询的子查询
		// DetachedCriteria sysDictTypeDc =
		// sysDictInfoByCgztDc.createCriteria("sysDictType");

		detachedCriteria.add(Restrictions.or(Restrictions.eq("sysDictInfoByCgzt.id", "01101"),Restrictions.eq("sysDictInfoByCgzt.id", "01102"),Restrictions.eq("sysDictInfoByCgzt.id", "01103")));
		
		// 添加采购时间条件
		if (yybusinessQuery.getStartDate() != null && !yybusinessQuery.getStartDate().equals("")) {
			// 开始时间 将字符串格式2015-09-09 转化成 日期格式
			Date StartDate = MyUtil.getDate2(yybusinessQuery.getStartDate());
			if (yybusinessQuery.getEndDate() != null && !yybusinessQuery.getEndDate().equals("")) {
				// 结束时间
				Date EndDate = MyUtil.getDate2(yybusinessQuery.getEndDate());
				detachedCriteria.add(Restrictions.between("tjsj", StartDate, EndDate));
			} else {
				detachedCriteria.add(Restrictions.ge("tjsj", StartDate));
			}
		} else {
			if (yybusinessQuery.getEndDate() != null && !yybusinessQuery.getEndDate().equals("")) {
				Date EndDate = MyUtil.getDate2(yybusinessQuery.getEndDate());
				detachedCriteria.add(Restrictions.le("tjsj", EndDate));
			}
		}
		if (yybusinessQuery.getYear() != null && !yybusinessQuery.getYear().equals("")) {

			// 将字符串日期转化成Date类型 起始年
			String year = yybusinessQuery.getYear();
			Date start = MyUtil.getDate4(year);
			// 将时间+1年
			Date end = MyUtil.getDate4((Integer.parseInt(year) + 1) + "");
			detachedCriteria.add(Restrictions.between("tjsj", start, end));
		}

		// 添加采购状态 判断条件
		if (yybusinessQuery.getSysDictInfoByCgzt() != null
				&& !("".equals(yybusinessQuery.getSysDictInfoByCgzt().getId()))) {
			detachedCriteria
					.add(Restrictions.eq("sysDictInfoByCgzt.id", yybusinessQuery.getSysDictInfoByCgzt().getId()));
		}

		// 判断是供应商 还是 卫生院 还是卫生室 dwWsy dwWss dwGys
		if ("dwWss".equals(yybusinessQuery.getDw())) {
			// 如果要是卫生室 ,添加条件
			detachedCriteria.add(Restrictions.eq("dwWss.id", yybusinessQuery.getDwId()));// 主键可以省略子查询
			// sysDictTypeDc.add(Restrictions.eq("typecode", "011")); //设置采购状态

		} else if ("dwGys".equals(yybusinessQuery.getDw())) {
			// 如果要是供应商
			detachedCriteria.add(Restrictions.eq("dwGys.id", yybusinessQuery.getDwId()));
			// sysDictTypeDc.add(Restrictions.eq("typecode", "012")); //设置采购状态

		} else if ("dwWsy".equals(yybusinessQuery.getDw())) {
			// 参训DwWsy对应的地区
			DwWsy dwWsy = dwWsyDo.findById(yybusinessQuery.getDwId());
			String areaId = dwWsy.getSysArea().getId();
			dwWssDc.createCriteria("sysArea").add(Restrictions.ilike("id", areaId, MatchMode.START));
			// sysDictTypeDc.add(Restrictions.eq("typecode", "010")); //设置采购状态
		}
		// 医院名称条件
		if (yybusinessQuery.getDwWss() != null && yybusinessQuery.getDwWss().getMc() != null
				&& !yybusinessQuery.getDwWss().getMc().equals("")) {
			dwWssDc.add(Restrictions.ilike("mc", yybusinessQuery.getDwWss().getMc(), MatchMode.ANYWHERE));
		}
		// 供应商
		if (yybusinessQuery.getDwGys() != null && yybusinessQuery.getDwGys().getMc() != null
				&& !yybusinessQuery.getDwGys().getMc().equals("")) {
			dwGysDc.add(Restrictions.ilike("mc", yybusinessQuery.getDwGys().getMc(), MatchMode.ANYWHERE));
		}
		// 采购单号条件
		if (yybusinessQuery.getYycgd() != null && yybusinessQuery.getYycgd().getId() != null
				&& !yybusinessQuery.getYycgd().getId().equals("")) {
			yycgdDc.add(Restrictions.like("id", yybusinessQuery.getYycgd().getId()));
		}
		// 流水号条件
		if (yybusinessQuery.getYpxx() != null && yybusinessQuery.getYpxx().getBm() != null
				&& !yybusinessQuery.getYpxx().getBm().equals("")) {
			ypxxDc.add(Restrictions.ilike("bm", yybusinessQuery.getYpxx().getBm(), MatchMode.ANYWHERE));
		}
		// 通用名称
		if (yybusinessQuery.getYpxx() != null && yybusinessQuery.getYpxx().getSpmc() != null
				&& !yybusinessQuery.getYpxx().getSpmc().equals("")) {
			ypxxDc.add(Restrictions.ilike("spmc", yybusinessQuery.getYpxx().getSpmc(), MatchMode.ANYWHERE));
		}

		/*
		 * //药品信息相关条件 Ypxx ypxx = yybusinessQuery.getYpxx(); if (ypxx !=null) {
		 * detachedCriteria.createCriteria("ypxx").add(getExample(ypxx)); }
		 */
		return detachedCriteria;
	}

	@Override
	public Integer getTotalRecordWithBussinessDetail(YybusinessQuery yybusinessQuery) {
		DetachedCriteria dc = getDetachedCriteriaWithBussinessDetail(yybusinessQuery);
		dc.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(dc);
		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}
		return 0;
	}

	@Override
	public List<Yybusiness> findAllWithBussinessDetail(YybusinessQuery yybusinessQuery, int pageQuery_star,
			int pageQuery_pageSize) {
		DetachedCriteria dc = this.getDetachedCriteriaWithBussinessDetail(yybusinessQuery);
		return (List<Yybusiness>) this.getHibernateTemplate().findByCriteria(dc, pageQuery_star, pageQuery_pageSize);

	}

	/*
	 * 通过 药品信息 和药品采购单做唯一查询
	 */
	@Override
	public Yybusiness findByUnique(Ypxx ypxx, Yycgd yycgd) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Yybusiness.class);
		detachedCriteria.add(Restrictions.eq("ypxx", ypxx));
		detachedCriteria.add(Restrictions.eq("yycgd", yycgd));
		List<Yybusiness> list = (List<Yybusiness>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	@Override
	public List<Map<String, Object>> findAllByMonth() {
		StringBuffer sqlBuffer = new StringBuffer();

		sqlBuffer.append("select to_char(cgd.shsj,'mm') yf,sum(yy.cgje)cgje ");
		sqlBuffer.append(" from YYBUSINESS yy,YYCGD cgd ");
		sqlBuffer.append(" where cgd.shsj IS NOT NULL ");
		sqlBuffer.append(" AND cgd.id = yy.YYCGD_ID ");
		// 及订单受理完成的---------------------------------------
		sqlBuffer.append(" and yy.cgzt = '01102' ");
		sqlBuffer.append("  GROUP BY to_char(cgd.shsj,'mm') ");
		// ---------------------------------------------------------------------
		sqlBuffer.append("  ORDER BY yf");

		Session session = this.getSessionFactory().getCurrentSession();
		List<Map<String, Object>> list = session.createSQLQuery(sqlBuffer.toString())
				.setResultTransformer(Transformers.aliasToBean(HashMap.class)).list();
		return list;
	}

}
