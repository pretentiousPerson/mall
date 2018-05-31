package com.baijia.mall.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baijia.mall.common.Const;
import com.baijia.mall.common.ServerResponse;
import com.baijia.mall.common.TokenCache;
import com.baijia.mall.dao.UserMapper;
import com.baijia.mall.pojo.User;
import com.baijia.mall.service.IUserService;
import com.baijia.mall.util.MD5Util;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * �û���¼
	 */
	@Override
	public ServerResponse<User> login(String username, String password) {
		// TODO Auto-generated method stub
		int resultCount = userMapper.checkUsername(username);
		if(resultCount == 0) {
			return ServerResponse.createByErrorMessage("�û���������");
		}
		
		String md5Password = MD5Util.MD5EncodeUtf8(password);
		User user = userMapper.selectLogin(username,md5Password);
		if(user == null) {
			return ServerResponse.createByErrorMessage("�������");
		}
		
		user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
		return ServerResponse.createBySuccess("��¼�ɹ�",user);
	}
	
	/**
	 * ע��
	 * @param user
	 * @return
	 */
	public ServerResponse<String> register(User user) {
        ServerResponse<String> validResponse = this.checkValid(user.getUsername(),
                Const.USERNAME);

        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);

        if (!validResponse.isSuccess()) {
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5����
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);

        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("ע��ʧ��");
        }

        return ServerResponse.createBySuccessMessage("ע��ɹ�");
    }
	
	public ServerResponse<String> checkValid(String str, String type) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
            //��ʼУ��
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);

                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("�û����Ѵ���");
                }
            }

            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);

                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("email�Ѵ���");
                }
            }
        } else {
            return ServerResponse.createByErrorMessage("��������");
        }

        return ServerResponse.createBySuccessMessage("У��ɹ�");
    }
	
	 public ServerResponse<String> selectQuestion(String username) {
	        ServerResponse<String> validResponse = this.checkValid(username, Const.USERNAME);

	        if (validResponse.isSuccess()) {
	            //�û�������
	            return ServerResponse.createByErrorMessage("�û�������");
	        }

	        String question = userMapper.selectQuestionByUsername(username);

	        if (org.apache.commons.lang3.StringUtils.isNotBlank(question)) {
	            return ServerResponse.createBySuccess(question);
	        }

	        return ServerResponse.createByErrorMessage("�һ�����������ǿյ�");
	    }
	 
	 
	 public ServerResponse<String> checkAnswer(String username, String question,
		        String answer) {
		        int resultCount = userMapper.checkAnswer(username, question, answer);

		        if (resultCount > 0) {
		            //˵�����⼰�����������û���,��������ȷ��
		            String forgetToken = UUID.randomUUID().toString();
		            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);

		            return ServerResponse.createBySuccess(forgetToken);
		        }

		        return ServerResponse.createByErrorMessage("����Ĵ𰸴���");
		    }
	 
	 public ServerResponse<String> forgetResetPassword(String username,
		        String passwordNew, String forgetToken) {
		        if (org.apache.commons.lang3.StringUtils.isBlank(forgetToken)) {
		            return ServerResponse.createByErrorMessage("��������,token��Ҫ����");
		        }

		        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);

		        if (validResponse.isSuccess()) {
		            //�û�������
		            return ServerResponse.createByErrorMessage("�û�������");
		        }

		        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);

		        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
		            return ServerResponse.createByErrorMessage("token��Ч���߹���");
		        }

		        if (org.apache.commons.lang3.StringUtils.equals(forgetToken, token)) {
		            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
		            int rowCount = userMapper.updatePasswordByUsername(username,
		                    md5Password);

		            if (rowCount > 0) {
		                return ServerResponse.createBySuccessMessage("�޸�����ɹ�");
		            }
		        } else {
		            return ServerResponse.createByErrorMessage(
		                "token����,�����»�ȡ���������token");
		        }

		        return ServerResponse.createByErrorMessage("�޸�����ʧ��");
		    }

		    public ServerResponse<String> resetPassword(String passwordOld,
		        String passwordNew, User user) {
		        //��ֹ����ԽȨ,ҪУ��һ������û��ľ�����,һ��Ҫָ��������û�.��Ϊ���ǻ��ѯһ��count(1),�����ָ��id,��ô�������true��count>0;
		        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(
		                    passwordOld), user.getId());

		        if (resultCount == 0) {
		            return ServerResponse.createByErrorMessage("���������");
		        }

		        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));

		        int updateCount = userMapper.updateByPrimaryKeySelective(user);

		        if (updateCount > 0) {
		            return ServerResponse.createBySuccessMessage("������³ɹ�");
		        }

		        return ServerResponse.createByErrorMessage("�������ʧ��");
		    }
		    
		    public ServerResponse<User> updateInformation(User user) {
		        //username�ǲ��ܱ����µ�
		        //emailҲҪ����һ��У��,У���µ�email�ǲ����Ѿ�����,���Ҵ��ڵ�email�����ͬ�Ļ�,���������ǵ�ǰ������û���
		        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),
		                user.getId());

		        if (resultCount > 0) {
		            return ServerResponse.createByErrorMessage("email�Ѵ���,�����email�ٳ��Ը���");
		        }

		        User updateUser = new User();
		        updateUser.setId(user.getId());
		        updateUser.setEmail(user.getEmail());
		        updateUser.setPhone(user.getPhone());
		        updateUser.setQuestion(user.getQuestion());
		        updateUser.setAnswer(user.getAnswer());

		        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);

		        if (updateCount > 0) {
		            return ServerResponse.createBySuccess("���¸�����Ϣ�ɹ�", updateUser);
		        }

		        return ServerResponse.createByErrorMessage("���¸�����Ϣʧ��");
		    }
		    
		    public ServerResponse<User> getInformation(Integer userId) {
		        User user = userMapper.selectByPrimaryKey(userId);

		        if (user == null) {
		            return ServerResponse.createByErrorMessage("�Ҳ�����ǰ�û�");
		        }

		        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);

		        return ServerResponse.createBySuccess(user);
		    }



}
