package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.untils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoimpl implements SellerDao {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	@Override
	public Seller findByid(int sid) {
		//定义sql
		String sql = "select * from tab_seller where sid = ? ";
		//执行sql
		return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
	}
}
