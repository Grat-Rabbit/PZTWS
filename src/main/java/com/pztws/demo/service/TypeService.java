package com.pztws.demo.service;

import com.pztws.demo.model.Article;
import com.pztws.demo.service.impl.TypeServiceImpl;
import com.pztws.demo.utils.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TypeService {

    /**
     * 查找当前所有的类型名字与id
     * @return
     */
    ArrayList<Map<String,String>> findTypeList();

    /**
     * 1.获取的是 list
     * 根据typeId获取typeName
     * @param typeIdList
     * @return
     */
    ArrayList<Map<String,String>> findTypeNameById(List<String> typeIdList);

    /**
     * 2.获取的是 单个
     * 根据typeId获取typeName
     * @param typeIdList
     * @return
     */
    Map<String,String> findTypeNameById(String typeIdList);
    /**
     * 改变文章的type
     * @param articleList
     * @param typeId
     * @param goalTypeId
     * @return
     */
    Result modifyArticleType(List<String> articleList, String typeId, String goalTypeId);


    /**
     * 增加新的文章分类
     * @param typeName
     * @return
     */
    boolean addType(String typeName);

    /**
     * 删除原有的文章分类名
     * @param typeId
     * @return
     */
    boolean deleteType(String typeId);

    /**
     * 修改现有的文章分类
     * @param typeId
     * @param typeName
     * @return
     */
    boolean modifyType(String typeId,String typeName);



}
