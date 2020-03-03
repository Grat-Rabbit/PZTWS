package com.pztws.demo.dao;

import com.pztws.demo.model.Article;
import com.pztws.demo.model.ArticleSmall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao {

    List<ArticleSmall> findTop();
    List<ArticleSmall> findWheel();
    List<Article> findAll();
    List<Article> findTypeArticles(String type);
    List<Article>findMyselfArticles(String userId);
    Article findArticle(String articleId);
    boolean deleteArticle(String articleId);

    Integer selectCount();//查询文章数目
    //因为没办法直接存入list，所以把list变成strin再存入
    boolean insertArticle( @Param("Article")Article article);

    boolean modifyArticle(@Param("Article") Article article);
}
