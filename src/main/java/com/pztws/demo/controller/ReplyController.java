package com.pztws.demo.controller;

import com.pztws.demo.service.impl.ReplyServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.MultiRequestBody;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.pztws.demo.utils.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "留言的Controller,包含非管理员对留言的全部的全部操作")
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyServiceImpl ReplyServiceImpl;
    Result result = new Result();

    @PostMapping("/list")
    @ResponseBody
    public Result showReplyList(@MultiRequestBody Integer pageNo,
                                @MultiRequestBody Integer pageSize) throws RuntimeException {
        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        result.setData(ReplyServiceImpl.findPage(pageNo, pageSize));
        result.setStatus(true);
        return result;
    }


    @PostMapping("/release")
    @UserLoginToken
    @ResponseBody
    public Result release(@MultiRequestBody String body) throws RuntimeException {
        ReplyServiceImpl.addReply(AuthenticationInterceptor.getUser().getUserId(), body);
        result.setData("添加成功");
        result.setStatus(true);
        return result;
    }

    @PostMapping("/delete")
    @UserLoginToken
    @ResponseBody
    public Result deleteReply(@MultiRequestBody String replyId) throws RuntimeException {
        ReplyServiceImpl.deleteReply(AuthenticationInterceptor.getUser(), replyId);
        result.setData("删除成功");
        result.setStatus(true);
        return result;
    }

    @PostMapping("/myself")
    @UserLoginToken
    @ResponseBody
    public Result myself(@MultiRequestBody Integer pageNo,
                         @MultiRequestBody Integer pageSize) throws RuntimeException {
        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        result.setData(ReplyServiceImpl.findMyself(AuthenticationInterceptor.getUser(),pageNo, pageSize));
        result.setStatus(true);
        return result;
    }



}