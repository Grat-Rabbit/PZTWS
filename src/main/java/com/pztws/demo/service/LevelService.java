package com.pztws.demo.service;

import com.pztws.demo.model.User;
import com.pztws.demo.utils.PageResult;

public interface LevelService {

    /**
     * 更改权限
     * @param user 当前改变他人的用户
     * @param userId  需要更改的用户id
     * @param level   需要更改成哪个权限等级
     * @return
     */
    boolean changeLevel(User user, String userId, int level);


    /**
     * 查看权限
     * @param department（需要查看的部门）
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<User> findLevelByDepartment(String department,Integer pageNo, Integer pageSize);

    /**
     * 查看全部权限
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<User> findLevel(Integer pageNo, Integer pageSize);

}
