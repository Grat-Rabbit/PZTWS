package com.pztws.demo.service;

import com.pztws.demo.model.Department;
import com.pztws.demo.utils.Result;
import com.pztws.demo.utils.annotation.MultiRequestBody;

import java.util.ArrayList;
import java.util.List;


public interface DepartmentService {

    /**
     * 查找当前所有的部门名字与id
     * @return
     */
    ArrayList<Department> findDepartmentList();

    /**
     * 根据id查询详细部门信息
     * @param departmentId
     */
   Department findDepartmentById(String departmentId);

    /**
     * 查找部门的详细信息
     * @return
     */
    ArrayList<Department> detailDepartment();

    /**
     * 增加新的部门
     * @param departmentName
     * @param departmentPic
     * @param brief
     * @return
     */
    boolean addDepartment(String departmentName,String departmentPic, String brief);

    /**
     * 删除一个部门
     * @param departmentId
     * @return
     */
    boolean deleteDepartment(String departmentId);

    /**
     * 修改部门信息
     * @param departmentId
     * @param departmentName
     * @param departmentPic
     * @param departmentBrief
     * @return
     */
    boolean modifyDepartment(String departmentId,String departmentName,String departmentPic,String departmentBrief);


    /**
     * 批量更改用户所属部门(高级版本 lv0）
     * @param userIdList
     * @param departmentId
     * @return
     */
    Result changeUserDepartment(List<String> userIdList, String departmentId,int now_level);

}
