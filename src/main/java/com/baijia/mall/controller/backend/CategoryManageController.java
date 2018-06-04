package com.baijia.mall.controller.backend;

import com.baijia.mall.common.Const;
import com.baijia.mall.common.ResponseCode;
import com.baijia.mall.common.ServerResponse;
import com.baijia.mall.pojo.User;
import com.baijia.mall.service.ICategoryService;
import com.baijia.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by geely
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��¼,���¼");
        }
        //У��һ���Ƿ��ǹ���Ա
        if(iUserService.checkAdminRole(user).isSuccess()){
            //�ǹ���Ա
            //�������Ǵ��������߼�
            return iCategoryService.addCategory(categoryName,parentId);

        }else{
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��¼,���¼");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //����categoryName
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��¼,���¼");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //��ѯ�ӽڵ��category��Ϣ,���Ҳ��ݹ�,����ƽ��
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"�û�δ��¼,���¼");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //��ѯ��ǰ�ڵ��id�͵ݹ��ӽڵ��id
//            0->10000->100000
            return iCategoryService.selectCategoryAndChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("��Ȩ�޲���,��Ҫ����ԱȨ��");
        }
    }








}
