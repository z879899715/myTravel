package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.untils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoimpl implements RouteImgDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public List<RouteImg> findByRid(int rid) {
		//定义sql
		String sql = "select * from tab_route_img where rid = ? ";
		//执行sql
		return template.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
	}
}
