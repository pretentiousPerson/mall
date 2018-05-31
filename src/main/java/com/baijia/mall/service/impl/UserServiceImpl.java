package com.baijia.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baijia.mall.common.ServerResponse;
import com.baijia.mall.dao.UserMapper;
import com.baijia.mall.pojo.User;
import com.baijia.mall.service.IUserService;
import com.baijia.mall.util.MD5Util;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Override
	public ServerResponse<User> login(String username, String password) {
		// TODO Auto-generated method stub
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("用户名不存在");
		}
		
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		User user = userMapper.selectLogin(username,md5Password);
		if(user == null) {
			return ServerResponse.createByErrorMessage("密码错误");
		}
		
		user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
		return ServerResponse.createBySuccess("登录成功",user);
	}

}
