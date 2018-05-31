package com.baijia.mall.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baijia.mall.pojo.User;

@Repository
public interface UserMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    
    User selectLogin(
    		@Param("username")String username,
    		@Param("password")String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int checkUsername(String username);
    
    int checkEmail(String email);
    
    String selectQuestionByUsername(String username);
    
    int checkAnswer(
    		@Param("username")String username,
    		@Param("question")String question,
    		@Param("answer")String answer);
    
    int updatePasswordByUsername(@Param("username")
    String username, @Param("passwordNew")
    String passwordNew);

    int checkPassword(@Param(value = "password")
    String password, @Param("userId")
    Integer userId);

    int checkEmailByUserId(@Param(value = "email")
    String email, @Param(value = "userId")
    Integer userId);
}