package com.pztws.demo.dao;

import com.pztws.demo.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;


@Mapper
public interface DepartmentDao {
    ArrayList<Department> findDepartmentList();
    ArrayList<Department> detailDepartment();

    Department findDepartmentById(String departmentId);
    Integer selectCount(); //查询当前个数，做id用
    boolean modifyUserDepartment( @Param("userId") String userId,@Param("departmentId") String departmentId);
    boolean insertDepartment(String new_id,String departmentName,String departmentPic, String departmentBrief);
    boolean deleteDepartment(String departmentId);
    boolean modifyDepartment(String departmentId,String departmentName,String departmentPic,String departmentBrief);

}
