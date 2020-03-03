package com.pztws.demo.dao;


import com.pztws.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
    public interface UserDao {
    User loginPhone(String mobile, String password);
    User loginId(String userId, String password);
    User findUserById(String userId);
    boolean modifyUser(String userId,String userName,String password,String userPic,String signature);
    List<User> findPartner( );

    boolean changeLevel(@Param("userId")String userId, @Param("level")int level);
    boolean change0Department(@Param("userId")String userId);//将现任社长的所属部门设置为空
    List<User>findLevel();
    List<User>findLevelbyDepartment(String departmentId);




}
