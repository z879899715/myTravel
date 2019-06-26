package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.myFavorite;

import java.util.List;

public interface FavoriteDao {

	/**
	 * 根据rid,uid 查询数据库 tab_favorite
	 * @param uid
	 * @param rid
	 * @return
	 */
	public Favorite findByRidAndUid(int rid, int uid);

	/**
	 * 根据rid 查询收藏次数
	 * @param rid
	 * @return
	 */
	public int findCountByRid(int rid);

	/**
	 * 根据rid和uid 添加数据到 tab_favorite表中
	 * @param uid
	 * @param rid
	 */
	public void add(int rid, int uid);

	/**
	 * 根据rid和uid 删除数据到 tab_favorite表中
	 * @param rid
	 * @param uid
	 */
	public void del(int rid, int uid);

	/**
	 * 根据uid查询出Favorite类型的集合
	 * @param uid
	 * @return
	 */
	public List<myFavorite> findByUid(int uid);


	public Route findRouteByRid(int rid);
}
