package com.pztws.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pztws.demo.dao.ReplyDao;
import com.pztws.demo.dao.UserDao;
import com.pztws.demo.model.Reply;
import com.pztws.demo.model.User;
import com.pztws.demo.service.ReplyService;
import com.pztws.demo.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Resource
    ReplyDao replyDao;
    @Resource
    UserDao userDao;

    @Override
    public PageResult<Reply> findPage(Integer pageNo, Integer pageSize) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            Page<Reply> page = (Page<Reply>) replyDao.findAll();
            PageResult<Reply> pageResult = new PageResult<>(page.getTotal(), page.getResult());
            return pageResult;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }
    }

    @Override
    public boolean addReply(String userId, String body) {
        if (replyDao.insertReply(userId, body)) {
            return true;
        } else throw new RuntimeException("数据插入失败");
    }

    @Override
    public boolean deleteReply(User user, String replyId) {
        String replyUserId =findReply(replyId).getUserId();
        int replyLevel =userDao.findUserById(replyUserId).getLevel().intValue();
        if (user.getUserId().equals(replyUserId)) { //自己发布的可删
            if (replyDao.deleteReply(replyId)) {
                return true;
            } else throw new RuntimeException("数据删除失败");
        }
        else if(user.getLevel().intValue()==2){ //编辑人员不可以删除部长和社长的留言
            if(replyLevel==1 ||replyLevel ==0){
                throw new RuntimeException("您无权删除此条留言");
            }
            else  {
                if(replyDao.deleteReply(replyId));
                else throw new RuntimeException("数据删除失败");
                return true;
            }
        }
        else if(user.getLevel().intValue()==1 || user.getLevel().intValue()==0) { //部长和社长可删除所有人的留言
            if (replyDao.deleteReply(replyId)) ;
            else throw new RuntimeException("数据删除失败");
            return true;
        }
        else throw new RuntimeException("您无权删除此条留言");
    }

    @Override
    public Reply findReply(String replyId) {
        Reply reply = replyDao.findReply(replyId);
        if(reply==null){
            throw new RuntimeException("没有此条留言");
        }
        return reply;
    }

    @Override
    public PageResult<Reply> findMyself(User user,Integer pageNo, Integer pageSize) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            Page<Reply> page = (Page<Reply>)replyDao.findMyselfList(user.getUserId());
            PageResult<Reply> pageResult = new PageResult<>(page.getTotal(), page.getResult());
            return pageResult;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }

    }



}
