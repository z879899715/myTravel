package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoimpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.myFavorite;
import cn.itcast.travel.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceimpl implements FavoriteService {

	private FavoriteDao dao = new FavoriteDaoimpl();

	@Override
	public boolean isFavorite(String rid, int uid) {
		//调用dao
		Favorite favorite = dao.findByRidAndUid(Integer.parseInt(rid), uid);
//		System.out.println(Integer.parseInt(rid));
//		System.out.println(uid);
//		System.out.println(favorite);


		return favorite != null;
	}

	@Override
	public void add(String rid, int uid) {
		dao.add(Integer.parseInt(rid),uid);
	}

	@Override
	public void del(String rid, int uid) {
		dao.del(Integer.parseInt(rid),uid);
	}

	@Override
	public List<Route> getMyFavorite(int uid) {
		//调用dao
		List<myFavorite> list = dao.findByUid(uid);
		//创建一个
		List<Route> routelist = new ArrayList<Route>();

		for (myFavorite myFavorite : list) {
			int rid = myFavorite.getRid();
			//调用dao获取线路对象
			Route route = dao.findRouteByRid(rid);
			routelist.add(route);
		}


		return routelist;
	}
}
