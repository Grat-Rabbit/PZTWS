package com.pztws.demo.controller;

import com.pztws.demo.service.impl.UserServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.MultiRequestBody;
import com.pztws.demo.utils.annotation.PassToken;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.pztws.demo.utils.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@Api(tags = "用户controller，包括登陆注册和修改个人信息以及申请入社，0代表失败，1代表成功.-1代表故障")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl UserServiceImpl;


    Result result =new Result();


    @PassToken
    @ApiOperation("登录 利用手机号码+密码的形式完成功登录")
    @PostMapping ("/login")
    public Result login(@MultiRequestBody String identity, @MultiRequestBody  String password){
        if(identity.matches("^\\d{11}$")){
            result.setData(UserServiceImpl.loginPhone(identity,password));
            result.setStatus(true);
        }
        else if(identity.matches("^\\d{7}$")){
            result.setData(UserServiceImpl.loginId(identity,password));
            result.setStatus(true);

        }
        else {
            result.setStatus(false);
            result.setData("输入的用户标识有误");
        }
        //result.setData(UserServiceImpl.login(phone,password));

        return result;
    }

    @ApiOperation("注册，推迟开发")
    @PostMapping ("/register")
   public void register(){
    }

    @UserLoginToken
    @ApiOperation("入社申请，推迟开发")
    @PostMapping ("/apply")
    public void apply(){
        System.out.println("正常");
    }

    @UserLoginToken
    @ApiOperation("查看个人详情页面")
    @GetMapping ("/info")
    public Result getUser(){
        result.setData(UserServiceImpl.getUser());
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @ApiOperation("更改个人信息,允许更改的只有用户名,密码,头像,个性签名")
    @PostMapping ("/modify")
    public Result modifyUser(@MultiRequestBody  String userName,
                                         @MultiRequestBody  String password,
                                         @MultiRequestBody  String userPic,
                                         @MultiRequestBody  String signature){
        if(UserServiceImpl.modifyUser(AuthenticationInterceptor.getUser().getUserId(),
                userName,password,userPic,signature)){
            result.setData("更改成功");
            result.setStatus(true);
        }
        return result;
    }




}

