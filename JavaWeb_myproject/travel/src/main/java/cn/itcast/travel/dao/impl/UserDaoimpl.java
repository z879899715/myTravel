package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.untils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoimpl implements UserDao {

	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

	/**
	 * 根据用户名查询是否存在用户
	 * @param username
	 * @return
	 */
	@Override
	public User findByUsername(String username) {
		//初始化 User
		User user = null;
		try {
			//定义sql语句
			String sql ="select * from tab_user where username = ? ";
			//执行sql
			user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
		} catch (Exception e) {

		}
		return user;
	}

	/**
	 * 存储注册信息
	 * @param user
	 */
	@Override
	public void save(User user) {
		//1.定义sql
		String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
		//2.执行sql
			template.update(sql, user.getUsername(),
					user.getPassword(),
					user.getName(),
					user.getBirthday(),
					user.getSex(),
					user.getTelephone(),
					user.getEmail(),
					user.getStatus(),
					user.getCode()
			);


	}

	/**
	 * 根据激活码找用户
	 * @param code
	 * @return
	 */
	@Override
	public User findByCode(String code) {
		User user = null;
		try {
			//定义sql
			String sql ="select * from tab_user where code = ? ";
			//执行sql
			user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return user;
	}

	/**
	 * 修改用户状态码,用于激活用户
	 * @param user
	 */
	@Override
	public void updateStatus(User user) {

		//定义sql
		String sql = "update tab_user set status = 'Y' where uid = ? ";
		//执行sql
		template.update(sql,user.getUid());
	}

	/**
	 * 根据用户名和密码查询用户,用于登录
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) {
		//定义 sql
		String sql = "select * from tab_user where username = ? and password = ? ";
		//执行sql
		User user = null;
		try {
			 user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return user;
	}
}
