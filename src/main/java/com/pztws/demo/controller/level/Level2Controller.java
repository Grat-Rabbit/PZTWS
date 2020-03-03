package com.pztws.demo.controller.level;

import com.pztws.demo.model.Article;
import com.pztws.demo.service.impl.ArticleServiceImpl;
import com.pztws.demo.service.impl.ReplyServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication2;
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

@Api(tags = "权限等级2的操作")
@RestController
public class Level2Controller {

    @Autowired
    ArticleServiceImpl ArticleServiceImpl;
    @Autowired
    ReplyServiceImpl ReplyServiceImpl;

    Result result = new Result();

    @UserLoginToken
    @LevelAuthentication2
    @ApiOperation("方法可以删除文章")
    @PostMapping("/article/admin/delete")
    public Result deleteArticle(@MultiRequestBody String articleId){
        //传两个值进去判断，权限2的小编不能删社长和部长权限的文章，其他都可
        ArticleServiceImpl.deleteArticle(AuthenticationInterceptor.getUser(),articleId);
        result.setData("删除成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication2
    @ApiOperation("方法可以删除他人发布的留言")
    @PostMapping("/reply/admin/delete")
    public Result deleteOtherReply(@MultiRequestBody String replyId){
        //传两个值进去判断，权限2的小编不能删社长和部长权限的留言，其他都可
        ReplyServiceImpl.deleteReply(AuthenticationInterceptor.getUser(),replyId);
        result.setData("删除成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication2        //判断是否属于此权限等级
    @PostMapping("/article/admin/release")
    @ApiOperation("增加文章")
    public Result addArticle(@MultiRequestBody String title,
                             @MultiRequestBody String shortBorder,
                             @MultiRequestBody String border,
                             @MultiRequestBody List<String> typeIdList,
                             @MultiRequestBody String picUrl,
                             @MultiRequestBody Integer indexStatus) {

        Article article = new Article();
        try {           //设置各文章格式
            article.setTitle(title);
            article.setShortBorder(shortBorder);
            article.setBorder(border);
            article.setTypeIdList(typeIdList);
            article.setTypeListString(String.join(",", typeIdList));
            article.setPicUrl(picUrl);
            article.setIndexStatus(indexStatus);
            article.setEditor(AuthenticationInterceptor.getUser().getUserName());
            article.setUserId(AuthenticationInterceptor.getUser().getUserId());
        }
        catch (RuntimeException e){
            throw new RuntimeException("文章格式有误");
        }
        ArticleServiceImpl.insertArticle(article);
        result.setData("增加成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication2        //判断是否属于此权限等级
    @PostMapping("/article/admin/modify")
    @ApiOperation("更改文章")
    public Result addArticle(@MultiRequestBody String articleId,
                             @MultiRequestBody String title,
                             @MultiRequestBody String shortBorder,
                             @MultiRequestBody String border,
                             @MultiRequestBody List<String> typeIdList,
                             @MultiRequestBody String picUrl,
                             @MultiRequestBody Integer indexStatus) {
        Article article = new Article();
        try {           //设置各文章格式
            article.setArticleId(articleId);
            article.setTitle(title);
            article.setShortBorder(shortBorder);
            article.setBorder(border);
            article.setTypeIdList(typeIdList);
            article.setPicUrl(picUrl);
            article.setIndexStatus(indexStatus);
            article.setTypeListString(String.join(",", typeIdList));
        }
        catch (RuntimeException e){
            throw new RuntimeException("文章格式有误");
        }
        ArticleServiceImpl.modifyArticle(article,AuthenticationInterceptor.getUser());
        result.setData("修改成功");
        result.setStatus(true);
        return result;
    }


    @UserLoginToken
    @LevelAuthentication2       //判断是否属于此权限等级
    @PostMapping("/article/admin/myself")
    @ApiOperation("查看自身发布的文章")
    public Result addArticle(@MultiRequestBody Integer pageNo,
                             @MultiRequestBody Integer pageSize) {
        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        result.setData(ArticleServiceImpl.findMyselfArticles(pageNo,pageSize,
                AuthenticationInterceptor.getUser().getUserId()));
        result.setStatus(true);
        return result;
    }

}
