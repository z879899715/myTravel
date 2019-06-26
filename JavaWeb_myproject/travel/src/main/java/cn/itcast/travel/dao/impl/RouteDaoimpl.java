package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.untils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoimpl implements RouteDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	@Override
	public int findTotalCount(int cid,String rname) {
		//定义sql
//		String sql = "select count(*) from tab_route where cid = ? ";
		//定义模板sql
		String sql = "select count(*) from tab_route where 1=1 ";
		StringBuilder sb = new StringBuilder(sql);
		//定义一个list集合 ,用于给 ? 赋值
		List params   = new ArrayList();
		//判断
		if(cid != 0){
			//如果不为0加上
			sb.append(" and cid =  ? ");
			params.add(cid);
		}
		
		if(rname != null && rname.length() > 0 && !"".equals(rname) && !"null".equals(rname)){
			sb.append(" and rname like ? ");
			params.add("%"+rname+"%");
		}
		sql = sb.toString();

		return template.queryForObject(sql,Integer.class,params.toArray());
	}

	@Override
	public List<Route> pageQuery(int cid, int start, int pageSize,String rname) {
		//定义sql
//		String sql = "select * from tab_route where cid = ? limit ? , ? ";
		//定义模板sql

		String sql = " select * from tab_route where 1=1  ";
		StringBuilder sb = new StringBuilder(sql);

		//定义一个list集合 ,用于给 ? 赋值
		List params  = new ArrayList();
		//判断
		if(cid != 0 ){
			//如果不为0加上
			sb.append(" and cid = ? ");
			params.add(cid);
		}

		if(rname != null && rname.length() > 0 && !"".equals(rname) && !"null".equals(rname)){

			sb.append(" and rname like ? ");
			params.add("%"+rname+"%");
		}
		sb.append(" limit ?,?");
		sql = sb.toString();
//		System.out.println(params.toString());
		//将参数添加到params中
		params.add(start);
		params.add(pageSize);


		return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
	}

	@Override
	public Route findOne(int rid) {
		//定义sql
		String sql = "select * from tab_route where rid = ? ";

		return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
	}
}
