package com.pztws.demo.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface TypeDao {
    List<Map<String,String>> findTypeList();
    Integer selectCount(); //查询当前个数，做id用
    boolean insertType(String typeId,String typeName);
    boolean deleteType(String typeId);
    boolean modifyType(String typeId,String typeName);

    Map<String ,String >findTypeById(String typeId);
    ArrayList<Map<String,String>> findTypeListById(List<String> typeIdList );
    boolean modifyArticleType(@Param("articleId") String articleId,@Param("typeId") String typeId,@Param("goalTypeId")String goalTypeId);
}
