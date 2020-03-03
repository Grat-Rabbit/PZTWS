package com.pztws.demo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pztws.demo.dao.ArticleDao;
import com.pztws.demo.dao.UserDao;
import com.pztws.demo.model.Article;
import com.pztws.demo.model.ArticleSmall;
import com.pztws.demo.model.User;
import com.pztws.demo.service.ArticleService;
import com.pztws.demo.utils.PageResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleDao articleDao;
    @Resource
    private UserDao UserDao;


    public List<ArticleSmall> findIndex(int n) {
        List<ArticleSmall> list;
        if (n == 1) list = articleDao.findTop(); //置顶是1
        else if (n == 2) list = articleDao.findWheel();   //轮播图是2
        else throw new RuntimeException("未知错误");
        if (list.size() == 0) throw new RuntimeException("内容为空");
        return list;
    }

    @Override
    public PageResult<Article> findAll(Integer pageNo, Integer pageSize) {
        try {
            PageHelper.startPage(pageNo, pageSize);
            Page<Article> page = (Page<Article>) articleDao.findAll();
            StringToList(page);
            PageResult<Article> pageResult = new PageResult<>(page.getTotal(), page.getResult());
            return pageResult;
        } catch (RuntimeException e) {
            throw new RuntimeException("内部错误，请联系管理员");
        }
    }


    @Override
    public PageResult<Article> findTypeArticles(String type, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        String likeType = "%" + type + "%";
        Page<Article> page = (Page<Article>) articleDao.findTypeArticles(likeType);
        StringToList(page);
        PageResult<Article> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public PageResult<Article> findMyselfArticles(Integer pageNo, Integer pageSize, String userId) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Article> page = (Page<Article>) articleDao.findMyselfArticles(userId);
        StringToList(page);
        PageResult<Article> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;

    }

    @Override
    public Article findArticle(String articleId) {
        Article article = articleDao.findArticle(articleId);
        article.setTypeIdList(Arrays.asList(article.getTypeListString().split(",")));
        if (article == null) {
            throw new RuntimeException("查询的文章不存在");
        }
        return article;
    }

    @Override
    public boolean deleteArticle(User user, String articleId) {
        String articleUserId = articleDao.findArticle(articleId).getUserId();
        int articleLevel = UserDao.findUserById(articleUserId).getLevel().intValue();
        if (user.getLevel().intValue() == 2 && articleLevel == 2) { //小编可删除与其同级的
            if (articleDao.deleteArticle(articleId)) ;
            else throw new RuntimeException("数据删除失败");
            return true;
        } else if (user.getLevel().intValue() == 0 || user.getLevel().intValue() == 1) { //社长和部长
            if (articleDao.deleteArticle(articleId)) ;
            else throw new RuntimeException("数据删除失败");
            return true;
        } else throw new RuntimeException("您无权删除");
    }


    public boolean insertArticle(Article article) {
        String new_id = "articleA".concat(articleDao.selectCount().toString());
        articleDao.selectCount();
        article.setArticleId(new_id);
        if (articleDao.insertArticle(article)) {
            return true;
        } else {
            throw new RuntimeException("插入有误");
        }
    }

    public boolean modifyArticle(Article article, User user) {
        Article article_old = articleDao.findArticle(article.getArticleId());
        if (article_old == null) {
            throw new RuntimeException("并没有此文章");
        }
        //同理，权限2的小编不能修改部长或者社长发布的文章
        else if (user.getLevel().intValue() == 0 || user.getLevel().intValue() == 1) {
            if (articleDao.modifyArticle(article)) {
                return true;
            } else {
                throw new RuntimeException("修改有误");
            }
        } else if (user.getLevel().intValue() == 2) {
            User article_old_user = UserDao.findUserById(article_old.getUserId());
            if (article_old_user.getUserId().equals(user.getUserId())) {
                if (articleDao.modifyArticle(article)) {
                    return true;
                } else {
                    throw new RuntimeException("修改有误");
                }
            } else throw new RuntimeException("您无权修改此人发布的文章");
        } else throw new RuntimeException("您无权修改文章");
    }


    private void StringToList(Page<Article> page) { //完成string变成list的转变
        for (int i = 0; i < page.getTotal(); i++) {
            page.get(i).setTypeIdList(Arrays.asList(page.get(i).getTypeListString().split(",")));
        }
    }

}
