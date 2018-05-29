package com.baijia.mall.service;

import com.baijia.mall.common.ServerResponse;
import com.baijia.mall.pojo.User;

public interface IUserService {

	ServerResponse<User> login(String username,String password);
}
