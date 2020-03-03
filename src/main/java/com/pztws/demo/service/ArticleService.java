package com.pztws.demo.service;

import com.pztws.demo.model.Article;
import com.pztws.demo.model.ArticleSmall;
import com.pztws.demo.model.User;
import com.pztws.demo.utils.PageResult;
import com.pztws.demo.utils.Result;

import java.util.List;


public interface ArticleService {
     /**
      * 查找位于首页的轮播文章内容和置顶文章内容
      * @param n    2：代表查找的是轮播内容；1：代表查找的是置顶内容
      * @return
      */
     List<ArticleSmall> findIndex(int n);

     /**
      * 查找所有文章
      * @return
      */
     PageResult<Article> findAll(Integer pageNo, Integer pageSize);


     /**
      * 查找不同类型的文章内容
      * @param type type:指文章类型
      * @return
      */
     PageResult<Article> findTypeArticles(String type,Integer pageNo, Integer pageSize);

     /**
      * 查找自己所写的的文章内容列表
      * @return
      */
     PageResult<Article> findMyselfArticles(Integer pageNo, Integer pageSize,String userId);

     /**
      * 查找对应id的文章中的全部内容
      * @param articleId articleId:对应文章的id
      * @return
      */
     Article findArticle(String articleId);


     /**
      * 删除对应文章
      * @param articleId
      * @return
      */
     boolean deleteArticle(User user , String articleId);

     /**
      * 增加对应文章
      * @param article
      * @return
      */
     boolean insertArticle(Article article);

     /**
      * 修改文章
      * @param article
      * @param user
      * @return
      */
     boolean modifyArticle(Article article,User user);

}
