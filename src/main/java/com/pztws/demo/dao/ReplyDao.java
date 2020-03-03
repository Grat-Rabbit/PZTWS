package com.pztws.demo.dao;

import com.pztws.demo.model.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDao {

    List<Reply> findAll();
    boolean insertReply(String userId,String body);
    boolean deleteReply(String replyId);
    Reply findReply(String replyId);
    List<Reply> findMyselfList(String userId);
}
