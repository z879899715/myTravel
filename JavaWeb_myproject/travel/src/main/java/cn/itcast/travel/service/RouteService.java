package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
	/**
	 * 根据参数封装pagebean对象
	 * @param cid	类别id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

	/**
	 * 根据 rid 查询  返回一个route对象
	 * @param rid
	 * @return
	 */
	public Route findOne(int rid);
}
