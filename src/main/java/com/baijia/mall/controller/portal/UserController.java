package com.baijia.mall.controller.portal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baijia.mall.common.Const;
import com.baijia.mall.common.ServerResponse;
import com.baijia.mall.pojo.User;
import com.baijia.mall.service.IUserService;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private IUserService iUserService;
	
	@RequestMapping("login.do")
	@ResponseBody
	public ServerResponse<User> login(String username,String password,
			HttpSession session){
		ServerResponse<User> response = iUserService.login(username,password);
		if(response.isSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
	}
}
