package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {


	public boolean registUser(User user);

	public boolean active(String code);

	public User login(User user);
}
