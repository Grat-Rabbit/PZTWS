package com.pztws.demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.pztws.demo.dao.UserDao;
import com.pztws.demo.model.User;
import com.pztws.demo.service.UserService;
import com.pztws.demo.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    public User user;
    Map<String, Object> map = Maps.newHashMap();

    @Override
    public Map<String,Object> loginPhone(String mobile, String password) {
             user= userDao.loginPhone(mobile,password);
            if(user!=null){
                map.put("user", user);
                map.put("token", getToken(user));
            }else{
                throw new RuntimeException("用户名或密码错误，登录失败");
            }
            return map;
    }
    @Override
    public Map<String,Object> loginId(String userId, String password){
        user= userDao.loginId(userId,password);
        if(user!=null){
            map.put("user", user);
            map.put("token", getToken(user));
        }else{
            throw new RuntimeException("用户名或密码错误，登录失败");
        }
        return map;
    }

    @Override
    public  User getUser() {
        if(user!=null){
            return user;
        }else {
            throw new RuntimeException("token值过期");
        }
    }

    @Override
    public Boolean modifyUser(String userId,String userName,String password,String userPic,String signature) {
            if (userId != null) {
                if(userDao.modifyUser(userId,userName,password,userPic,signature)){
                    return true;
                }else{
                    throw new RuntimeException("用户数据更改失败");
                }

            } else {
                throw new RuntimeException("token值过期");
            }
    }

    @Override
    public User findUserById(String userId) {
        user = userDao.findUserById(userId);
        return  user;
    }


    @Override
    public PageResult<User> findPartner(Integer pageNo, Integer pageSize) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            Page<User> page = (Page<User>)userDao.findPartner();
            PageResult<User> pageResult = new PageResult<>(page.getTotal(), page.getResult());
            return pageResult;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }
    }



    @Override
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getUserId())
                .sign(Algorithm.HMAC256(user.getUserId()));
        return token;
    }
}
