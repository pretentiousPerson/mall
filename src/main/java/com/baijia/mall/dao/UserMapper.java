package com.baijia.mall.dao;

import org.springframework.stereotype.Repository;

import com.baijia.mall.pojo.User;

@Repository
public interface UserMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}