package com.pztws.demo.service;

import com.pztws.demo.model.Reply;
import com.pztws.demo.model.User;
import com.pztws.demo.utils.PageResult;

import java.util.ArrayList;
import java.util.Map;

public interface UserService {

    /**
     * 登陆验证
     * @param mobile 手机号码
     * @param password  密码
     * @return result
     * 其中0代表失败，1代表成功.-1代表故障
     */
    Map<String,Object> loginPhone(String mobile, String password);

    /**
     * 登陆验证
     * @param userId 用户id
     * @param password  密码
     * @return result
     * 其中0代表失败，1代表成功.-1代表故障
     */
    Map<String,Object> loginId(String userId, String password);

    /**
     * 根据之前登陆时获取到的User,返回给页面
     * @return User对象+判断
     */
    User getUser();

    /**
     * 更改用户信息
     * @return 新的User对象+判断
     */
    Boolean modifyUser(String userId,String userName,String password,String userPic,String signature);

    /**
     * 主要用于jwt查询此id用户是否与token中的用户一致
     * @param userId
     * @return
     */
    User findUserById(String userId);

    /**
     * 获得token
     * @param user
     * @return
     */
    String getToken(User user);


    /**
     * 可以查看权限等级为0，1，2，3的其他人的公开信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<User> findPartner(Integer pageNo, Integer pageSize);


}
