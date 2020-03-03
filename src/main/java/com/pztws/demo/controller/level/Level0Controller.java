package com.pztws.demo.controller.level;

import com.pztws.demo.service.impl.DepartmentServiceImpl;
import com.pztws.demo.service.impl.LevelServiceImpl;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication0;
import com.pztws.demo.utils.annotation.LevelAuthentication.LevelAuthentication1;
import com.pztws.demo.utils.annotation.MultiRequestBody;
import com.pztws.demo.utils.annotation.UserLoginToken;
import com.pztws.demo.utils.interceptor.AuthenticationInterceptor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "权限等级0的操作")
@RestController
public class Level0Controller {

    @Autowired
    LevelServiceImpl LevelServiceImpl;

    @Autowired
    DepartmentServiceImpl DepartmentServiceImpl;

    Result result = new Result();

//    @UserLoginToken
//    @LevelAuthentication0
//    @ApiOperation("此方法可以更改高级权限")
//    @PostMapping("/admin/dominate/changeLevel")
//    public Result changeLevel(@MultiRequestBody String userId,
//                              @MultiRequestBody Integer level) {
//        if (level.intValue() > 4) {
//            result.setData("权限设置错误");
//            result.setStatus(false);
//        }
//        if (level.intValue() == 0) {
//            LevelServiceImpl.changeLevel(AuthenticationInterceptor.getUser(), userId, level.intValue());
//            result.setData("感谢你对社团的付出");
//            result.setStatus(true);
//        }
//        else {
//            LevelServiceImpl.changeLevel(AuthenticationInterceptor.getUser(), userId, level.intValue());
//            result.setData("修改成功");
//            result.setStatus(true);
//        }
//        return result;
//    }


    @UserLoginToken
    @LevelAuthentication0
    @ApiOperation("此方法可以根据部门名称查看权限")
    @PostMapping("/admin/findDepartmentLevel")
    public Result findLevel(@MultiRequestBody String  departmentId,
                            @MultiRequestBody Integer pageNo,
                            @MultiRequestBody Integer pageSize){

        if (pageNo <= 0)
            pageNo = 1;
        if (pageSize <= 0)
            pageSize = 10;
        if(AuthenticationInterceptor.getUser().getLevel()==0){
            result.setData(LevelServiceImpl.findLevelByDepartment(departmentId,pageNo, pageSize));
            result.setStatus(true);}
        else throw new RuntimeException("系统出错，请联系管理员");
        return result;
    }

    @UserLoginToken
    @LevelAuthentication0
    @ApiOperation("增加部门")
    @PostMapping("/department/admin/add")
    public Result addDepartment(@MultiRequestBody String  departmentName,
                            @MultiRequestBody String departmentPic,
                            @MultiRequestBody String departmentBrief){
        DepartmentServiceImpl.addDepartment(departmentName,departmentPic, departmentBrief);
        result.setData("增加成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication0
    @ApiOperation("删除部门")
    @PostMapping("/department/admin/delete")
    public Result deleteDepartment(@MultiRequestBody String  departmentId){
        DepartmentServiceImpl.deleteDepartment(departmentId);
        result.setData("删除成功");
        result.setStatus(true);
        return result;
    }

    @UserLoginToken
    @LevelAuthentication0
    @ApiOperation("修改部门信息")
    @PostMapping("/department/admin/modify")
    public Result modifyDepartment(@MultiRequestBody String  departmentId,
                                   @MultiRequestBody String  departmentName,
                                   @MultiRequestBody String  departmentPic,
                                   @MultiRequestBody String  departmentBrief){
        DepartmentServiceImpl.modifyDepartment(departmentId,departmentName,departmentPic,departmentBrief);
        result.setData("修改成功");
        result.setStatus(true);
        return result;
    }


}
