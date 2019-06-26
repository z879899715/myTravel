package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoimpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.untils.MailUtils;
import cn.itcast.travel.untils.UuidUtil;

public class UserServiceimpl implements UserService {

	private UserDao dao = new UserDaoimpl();

	/**
	 *用户注册
	 * @param user
	 * @return
	 */
	@Override
	public boolean registUser(User user) {

		//调用dao
		User u = dao.findByUsername(user.getUsername());
		//判断
		if(u == null){

			//设置激活码
			user.setCode(UuidUtil.getUuid());
			user.setStatus("N");
			//用户不存在,调用dao的存储方法
			dao.save(user);

			//激活邮件发送,邮件正文
			String content ="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
			MailUtils.sendMail(user.getEmail(),content,"激活邮件");
			return true;
		}

		//返回true
		return false;

	}

	/**
	 * 激活用户
	 * @return
	 * @param code
	 */
	@Override
	public boolean active(String code) {

	//根据激活码寻找用户
		User user = dao.findByCode(code);
		//判断
		if(user != null){
			//修改用户的状态码信息
			dao.updateStatus(user);
			return true;
		}else{
			return false;
		}






	}

	/**
	 * 登录
	 * @param user
	 * @return
	 */
	@Override
	public User login(User user) {
		//调用User dao

		return dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}
}
