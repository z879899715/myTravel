package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
	/**
	 * 获取总记录数
	 * @param cid
	 * @return
	 */
	public int findTotalCount(int cid,String rname);

	/**
	 * 分页查询的数据
	 * @return
	 * @param cid
	 * @param start
	 * @param pageSize
	 */
	public List<Route> pageQuery(int cid, int start, int pageSize,String rname);

	public Route findOne(int rid);
}
