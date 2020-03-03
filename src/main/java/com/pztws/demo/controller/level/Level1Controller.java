package com.pztws.demo.controller.level;

import com.pztws.demo.service.impl.DepartmentServiceImpl;
import com.pztws.demo.service.impl.LevelServiceImpl;
import com.pztws.demo.service.impl.TypeServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication1;
import com.pztws.demo.utils.annotation.MultiRequestBody;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.pztws.demo.utils.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "权限等级1的操作")
@RestController
public class Level1Controller {

    @Autowired
    LevelServiceImpl LevelServiceImpl;
    @Autowired
    DepartmentServiceImpl DepartmentServiceImpl;
    @Autowired
    TypeServiceImpl TypeServiceImpl;

    Result result = new Result();

//    @UserLoginToken
//    @LevelAuthentication1
//    @ApiOperation("此方法可以更改权限")
//    @PostMapping("/admin/changeLevel")
//    public Result changeLevel(@MultiRequestBody String userId,
//                              @MultiRequestBody Integer level){
//        if(level.intValue()>4){
//            result.setData("权限设置错误");
//            result.setStatus(false);
//        }
//        else {
//            LevelServiceImpl.changeLevel(AuthenticationInterceptor.getUser(),userId,level.intValue());
//            result.setData("删除成功");
//            result.setStatus(true);
//        }
//        return result;
//    }
//

    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("此方法可以查看权限")
    @PostMapping("/admin/findLevel")
    public Result findLevel(@MultiRequestBody Integer pageNo,
                              @MultiRequestBody Integer pageSize){

        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;

        if(AuthenticationInterceptor.getUser().getLevel()==1){//如果是1权限用户，查询同部门的level
        result.setData(LevelServiceImpl.findLevelByDepartment(AuthenticationInterceptor.getUser().getDepartmentId(),
                pageNo, pageSize));
        result.setStatus(true);}
        else if(AuthenticationInterceptor.getUser().getLevel()==0){ //如果是0权限用户，查询全部的level
            result.setData(LevelServiceImpl.findLevel(pageNo, pageSize));
            result.setStatus(true);
        }
        else throw new RuntimeException("系统出错，请联系管理员");
        return result;
    }

 //更改权限高低级版本合并
    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("此方法可以更改权限")
    @PostMapping("/admin/changeLevel")
    public Result changeLevel(@MultiRequestBody String userId,
                              @MultiRequestBody Integer level){
        if(level.intValue()>4){
            result.setData("权限设置错误");
            result.setStatus(false);
        }

        if(level.intValue()==0){
            LevelServiceImpl.changeLevel(AuthenticationInterceptor.getUser(),userId,level.intValue());
            result.setData("社长，这一年，辛苦你了");
            result.setStatus(true);
        }
        else {
            LevelServiceImpl.changeLevel(AuthenticationInterceptor.getUser(),userId,level.intValue());
            result.setData("更改成功");
            result.setStatus(true);
        }
        return result;
    }


    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("此方法增加分类名")
    @PostMapping("/article/admin/addType")
    public Result addType(@MultiRequestBody String typeName){
        TypeServiceImpl.addType(typeName);
        result.setData("增加成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("此方法删除分类名")
    @PostMapping("article/admin/deleteType")
    public Result deleteType(@MultiRequestBody String typeId){
        TypeServiceImpl.deleteType(typeId);
        result.setData("删除成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("此方法修改分类名")
    @PostMapping("article/admin/modifyType")
    public Result modifyType(@MultiRequestBody String typeName,
                             @MultiRequestBody String typeId){
        TypeServiceImpl.modifyType(typeId,typeName);
        result.setData("分类名修改成功");
        result.setStatus(true);
        return result;
    }


    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("批量更改用户所属部门(高低级版本合并)")
    @PostMapping("/admin/changeUser")
    public Result changeUserDepartment(@MultiRequestBody List<String> userIdList,
                              @MultiRequestBody String departmentId){
        if(userIdList==null || departmentId==null) {       //先判断userIdList 是否存在
            result.setData("数据未选中");
            result.setStatus(false);
            return result;
        }
        if(DepartmentServiceImpl.findDepartmentById(departmentId)==null) //如果此部门id存在，则会继续走下去
        {
            result.setData("部门Id有误");
            result.setStatus(false);
            return result;
        }

        //做权限等级判断和分流
        if(AuthenticationInterceptor.getUser().getLevel().intValue()==0){
            return DepartmentServiceImpl.changeUserDepartment(userIdList,departmentId,0); //LV0版本
        }
        else {
            return DepartmentServiceImpl.changeUserDepartment(userIdList,departmentId,1);  //Lv1 版本
        }
    }

//-------------------------可改-------//1
    @UserLoginToken
    @LevelAuthentication1
    @ApiOperation("文章迁移（群体文章修改分类）")
    @PostMapping("/article/admin/migrate")
    public Result articleMigrate(@MultiRequestBody List<String> articleIdList,
                                 @MultiRequestBody String typeId,
                                 @MultiRequestBody String goalTypeId){
        if(articleIdList==null || typeId==null  ||goalTypeId==null) {       //判断数据完整性
            result.setData("数据未选中");
            result.setStatus(false);
            return result;
        }
        //如果此分类id是否存在
        if(TypeServiceImpl.findTypeNameById(typeId)==null || TypeServiceImpl.findTypeNameById(goalTypeId)==null)
        {
            result.setData("分类Id有误");
            result.setStatus(false);
            return result;
        }
        return TypeServiceImpl.modifyArticleType(articleIdList,typeId,goalTypeId);
    }


}
