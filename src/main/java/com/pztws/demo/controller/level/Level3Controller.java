package com.pztws.demo.controller.level;

import com.pztws.demo.model.Article;
import com.pztws.demo.service.impl.ArticleServiceImpl;
import com.pztws.demo.service.impl.ReplyServiceImpl;
import com.pztws.demo.service.impl.UserServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication0;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication3;
import com.pztws.demo.utils.annotation.MultiRequestBody;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.pztws.demo.utils.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "权限等级3的操作")
@RestController
public class Level3Controller {
    @Autowired
    UserServiceImpl UserServiceImpl;
    @Autowired
    ArticleServiceImpl ArticleServiceImpl;

    Result result = new Result();

    @UserLoginToken
    @LevelAuthentication3        //判断是否属于此权限等级
    @ApiOperation("查看他人公开信息")
    @PostMapping("/user/findPartner")
    public Result findPartner(@MultiRequestBody Integer pageNo,
                          @MultiRequestBody Integer pageSize){
        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        result.setData(UserServiceImpl.findPartner(pageNo, pageSize));
        result.setStatus(true);
        return result;
    }



}
