package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.myFavorite;
import cn.itcast.travel.untils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoimpl implements FavoriteDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());


	@Override
	public Favorite findByRidAndUid(int rid, int uid) {
		//定义sql
		String sql = "select * from tab_favorite where uid = ? and rid = ? ";
		Favorite favorite = null;
		try {
			favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class),uid,rid);
		} catch (DataAccessException e) {

		}
//		System.out.println("uid"+uid);
//		System.out.println("rid"+rid);
//		System.out.println("favorite:"+favorite);
		return favorite;
	}

	@Override
	public int findCountByRid(int rid) {
		//定义sql
		String sql = "select count(*) from tab_favorite where rid = ? ";

		return template.queryForObject(sql,Integer.class,rid);
	}

	@Override
	public void add(int rid, int uid) {
		//定义sql
		String sql = "insert into tab_favorite values(?,?,?) ";

		//执行sql
		template.update(sql, rid,new Date(),uid);
	}

	@Override
	public void del(int rid, int uid) {
		//定义sql
		String sql = "delete from tab_favorite where rid = ? and uid = ? ";
		//执行sql
		template.update(sql, rid,uid);
	}

	@Override
	public List<myFavorite> findByUid(int uid) {
		//定义sql
		String sql = "select * from tab_favorite where uid = ? ";
		//执行

		return template.query(sql,new BeanPropertyRowMapper<myFavorite>(myFavorite.class),uid);
	}

	@Override
	public Route findRouteByRid(int rid) {
		String sql = "select * from tab_route where rid = ? ";

		return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
	}
}
