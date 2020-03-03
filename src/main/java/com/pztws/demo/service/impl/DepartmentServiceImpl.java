package com.pztws.demo.service.impl;

import com.pztws.demo.dao.DepartmentDao;
import com.pztws.demo.dao.TypeDao;
import com.pztws.demo.dao.UserDao;
import com.pztws.demo.model.Department;
import com.pztws.demo.service.DepartmentService;
import com.pztws.demo.utils.Result;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    DepartmentDao DepartmentDao;
    @Resource
    UserDao UserDao;

    Result result = new Result();
    @Override
    public ArrayList<Department>   findDepartmentList() {
        try{
            ArrayList<Department>  list=DepartmentDao.findDepartmentList();
            return list;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }
    }

    @Override
    public Department findDepartmentById(String departmentId) {
        try{
            return DepartmentDao.findDepartmentById(departmentId);
        }
        catch (RuntimeException e){
            throw new RuntimeException("没有此部门名字，请先创建");
        }
    }

    @Override
    public  ArrayList<Department> detailDepartment(){
        try{
            ArrayList<Department>  list=DepartmentDao.detailDepartment();
            return list;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }
    }

    @Override
    public boolean addDepartment(String departmentName, String departmentPic, String brief) {
        String new_id = "name_".concat(DepartmentDao.selectCount().toString());
        if (DepartmentDao.insertDepartment(new_id,departmentName, departmentPic,brief)) {
            return true;
        } else throw new RuntimeException("部门添加失败");
    }

    @Override
    public boolean deleteDepartment(String departmentId) {
        if (DepartmentDao.deleteDepartment(departmentId)) {
            return true;
        } else throw new RuntimeException("部门删除失败");
    }

    @Override
    public boolean modifyDepartment(String departmentId, String departmentName, String departmentPic, String departmentBrief) {
        if (DepartmentDao.modifyDepartment(departmentId,departmentName,departmentPic,departmentBrief)) {
            return true;
        } else throw new RuntimeException("部门修改失败");
    }

    @Override
    public Result changeUserDepartment(List<String> userIdList, String departmentId,int now_level) {
        int defeat=0;
        int success=0;
        for(int i=0;i<userIdList.size();i++){
            if(UserDao.findUserById(userIdList.get(i))==null){
                defeat++;
                continue;
            }
            int level=UserDao.findUserById(userIdList.get(i)).getLevel().intValue();
            if(now_level>=level){
                defeat++;
                continue;
            }
            //记录次数
            if(DepartmentDao.modifyUserDepartment(userIdList.get(i),departmentId)==false) defeat++;//这个只是为了预防错误bug
            else success++;
        }
        if(defeat==0){
            result.setStatus(true);
            result.setData("全部更改成功");
        }
        else {
            result.setStatus(false);
            result.setData("只有"+success+"个更改成功，剩下" + defeat + "个无法正确更改");
        }
        return result;
    }

}
