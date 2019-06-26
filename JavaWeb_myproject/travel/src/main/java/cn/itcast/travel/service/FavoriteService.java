package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriteService {
	/**
	 * 通过rid&uid查询,返回一个boolean值
	 * @param rid
	 * @param uid
	 * @return
	 */
	public boolean isFavorite(String rid,int uid);

	/**
	 * 添加收藏
	 * @param rid
	 * @param uid
	 */
	public void add(String rid,int uid);

	/**
	 * 删除收藏
	 * @param rid
	 * @param uid
	 */
	public void del(String rid,int uid);

	/**
	 * 通过uid查询 返回一个route类型的list集合
	 * @param uid
	 * @return
	 */
	public List<Route> getMyFavorite(int uid);
}
