package com.pztws.demo.controller;

import com.pztws.demo.dao.ArticleDao;
import com.pztws.demo.model.Article;
import com.pztws.demo.service.DepartmentService;
import com.pztws.demo.service.impl.ArticleServiceImpl;
import com.pztws.demo.service.impl.DepartmentServiceImpl;
import com.pztws.demo.service.impl.ReplyServiceImpl;
import com.pztws.demo.service.impl.TypeServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication2;
import com.pztws.demo.utils.annotation.MultiRequestBody;
import com.pztws.demo.utils.annotation.PassToken;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.pztws.demo.utils.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = "展示的controller,包含首页内容和非管理员浏览文章的全部操作")
@RestController
public class ShowController {
    @Autowired
    private ArticleServiceImpl articleServiceImpl;

    @Autowired
    private DepartmentServiceImpl DepartmentServiceImpl;

    @Autowired
    private TypeServiceImpl TypeServiceImpl;

    Result result = new Result();


    @GetMapping("index/wheel")
    @ApiOperation("展示轮播图")
    public Result wheel() {
        result.setData(articleServiceImpl.findIndex(2));
        result.setStatus(true);
        return result;
    }

    @GetMapping("index/sticky")
    @ApiOperation("展示置顶文章")
    public Result top() {
        result.setData(articleServiceImpl.findIndex(1));
        result.setStatus(true);
        return result;
    }


    @PostMapping("article/list")
    @ApiOperation("显示所有文章")
    public Result findAll(@MultiRequestBody Integer pageNo,
                          @MultiRequestBody Integer pageSize) {

        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        result.setData(articleServiceImpl.findAll(pageNo, pageSize));
        result.setStatus(true);
        return result;
    }

    @PostMapping("article/findTypeArticle")
    @ApiOperation("根据分类显示文章内容")
    public Result findTypeArticles(@MultiRequestBody String typeId,
                                   @MultiRequestBody Integer pageNo,
                                   @MultiRequestBody Integer pageSize) {

        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        if(typeId==null){
            throw  new RuntimeException("传入的typeId为空");
        }
        result.setData(articleServiceImpl.findTypeArticles(typeId, pageNo, pageSize));
        result.setStatus(true);
        return result;
    }

    @ApiOperation("获取现有的分类名")
    @GetMapping("/article/allTypeName")
    public Result getAllTypeName() {
        result.setData(TypeServiceImpl.findTypeList());
        result.setStatus(true);
        return result;
    }

    @ApiOperation("根据typeId获取typeName")
    @PostMapping("/article/getTypeName")
    public Result findTypeNameById(@MultiRequestBody ArrayList<String> typeIdList) {
        if(typeIdList==null){
            result.setData("没有传入任何数据");
            result.setStatus(true);
        }
        else{
            result.setData(TypeServiceImpl.findTypeNameById(typeIdList));
            result.setStatus(true);
        }
        return result;
    }

    @GetMapping("/index/department")
    @ApiOperation("展示社团部门信息")
    public Result detailDepartment() {
        result.setData(DepartmentServiceImpl.detailDepartment());
        result.setStatus(true);
        return result;
    }


    @ApiOperation("获取现有的部门名字和id")
    @GetMapping("/allDepartmentName")
    public Result departmentName() {
        result.setData(DepartmentServiceImpl.findDepartmentList());
        result.setStatus(true);
        return result;
    }

//    @GetMapping("/findDepartmentById")
//    @ApiOperation("根据id查询部门信息")
//    public Result findDepartmentById(@MultiRequestBody String departmentId){
//        result.setData(DepartmentServiceImpl.findDepartmentById(departmentId));
//        result.setStatus(true);
//        return result;
//    }


    @PostMapping("article")
    @ApiOperation("根据id显示文章内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true)
    })
    public Result findArticle(@MultiRequestBody String articleId) {
        if(articleId==null){
            throw new RuntimeException("传入数据为空");
        }
        result.setData(articleServiceImpl.findArticle(articleId));
        result.setStatus(true);
        return result;
    }

    //    @PostMapping("article/addViews")
//    @ApiOperation("根据id文章增加当前文章的阅读量,返回status判断，以及在message中存放变化后的阅读量")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="articleId",value="文章id",required=true)
//    })
//    public Result addViews(@RequestBody Map<String ,Object> paramsMap){
//        String articleId = paramsMap.get("articleId").toString();
//        Result result = new Result(1,"123");
//        return result;
//    }



}
