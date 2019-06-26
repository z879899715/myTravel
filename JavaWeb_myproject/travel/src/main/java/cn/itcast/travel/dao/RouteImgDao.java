package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
	/**
	 * 根据rid 查询出一个routeimg对象
	 * @param rid
	 * @return
	 */
	public List<RouteImg> findByRid(int rid);
}
