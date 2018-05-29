package com.baijia.mall.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baijia.mall.common.ServerResponse;
import com.baijia.mall.pojo.User;
import com.baijia.mall.util.MD5Util;

public class LoginTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void login() {
		User user = new User();
		user.setUsername("root");
		user.setPassword("root");
		assertEquals(ServerResponse.createBySuccess(user),new UserServiceImpl().login("root","root"));
	}

}
