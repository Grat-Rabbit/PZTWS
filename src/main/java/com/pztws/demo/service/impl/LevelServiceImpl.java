package com.pztws.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pztws.demo.dao.DepartmentDao;
import com.pztws.demo.dao.UserDao;
import com.pztws.demo.model.User;
import com.pztws.demo.service.LevelService;
import com.pztws.demo.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LevelServiceImpl implements LevelService {
    @Resource
    UserDao userDao;
    @Resource
    DepartmentDao DepartmentDao;

    @Override
    public boolean changeLevel(User user, String userId, int level) {
        int current = user.getLevel();
        int before_level =userDao.findUserById(userId).getLevel();

        //在controller对数据进行判断，此时进来的数据，current=0-1 level=0-4
        if ( (current==0 || current==1) && current< level && current<before_level) {
            if (userDao.changeLevel(userId, level)) return true;
        }

        if (current == 0 && level == 0) { //社长将他人设为了社长
            if (userDao.changeLevel(userId, level)) {
                if (userDao.changeLevel(user.getUserId(), 4)) {
                    userDao.change0Department(userId);
                    return true;
                }
            }
        }
        else if(current>=before_level || current>= level ) throw new RuntimeException("没有权限更改哦");
        throw new RuntimeException("更改失败");
    }

    @Override
    public PageResult<User> findLevelByDepartment(String departmentId, Integer pageNo, Integer pageSize) {
        try {
            System.out.println(departmentId);
            PageHelper.startPage(pageNo, pageSize);
            Page<User> page = (Page<User>) userDao.findLevelbyDepartment(departmentId);
            PageResult<User> pageResult = new PageResult<>(page.getTotal(), page.getResult());
            return pageResult;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }

    }

    @Override
    public PageResult<User> findLevel(Integer pageNo, Integer pageSize) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            Page<User> page = (Page<User>) userDao.findLevel();
            PageResult<User> pageResult = new PageResult<>(page.getTotal(), page.getResult());
            return pageResult;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }

    }

}
