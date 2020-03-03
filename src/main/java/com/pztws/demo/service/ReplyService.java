package com.pztws.demo.service;

import com.pztws.demo.model.Article;
import com.pztws.demo.model.Reply;
import com.pztws.demo.model.User;
import com.pztws.demo.utils.PageResult;

import java.util.List;

public interface ReplyService {

    /**
     * 查询全部留言内容
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<Reply> findPage(Integer pageNo, Integer pageSize);

    /**
     * 添加留言
     */
    boolean addReply(String userId,String body);

    /**
     * 删除留言
     * 需要有user判断是否是删除的自己留言
     * @param replyId
     * @return
     */
    boolean deleteReply(User user, String replyId);

    /**
     * 查找对应留言
     * 根据id查找对应的留言
     * @param replyId
     * @return
     */
    Reply findReply(String replyId);


    /**
     * 显示用户自身发布的全部留言,因为数量不明，所以需要分页
     * @return
     */
     PageResult<Reply> findMyself(User user,Integer pageNo, Integer pageSize) ;

}
