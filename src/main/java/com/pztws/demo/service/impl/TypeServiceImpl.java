package com.pztws.demo.service.impl;


import com.pztws.demo.dao.ArticleDao;
import com.pztws.demo.dao.TypeDao;
import com.pztws.demo.service.TypeService;
import com.pztws.demo.utils.Result;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {
    @Resource
    TypeDao TypeDao;

    @Resource
    ArticleDao ArticleDao;

    Result result = new Result();

    @Override
    public ArrayList<Map<String,String>> findTypeList(){
        try{
            ArrayList<Map<String,String>>list =(ArrayList<Map<String,String>>) TypeDao.findTypeList();
            return list;
        }
        catch (RuntimeException e){
            throw new RuntimeException("内部错误，请联系管理员");
        }
    }

    @Override
    public ArrayList<Map<String,String>> findTypeNameById(List<String> typeIdList){
        ArrayList<Map<String,String>> typeNameList=TypeDao.findTypeListById(typeIdList);
        if(typeNameList.size()==0){
            throw new RuntimeException("查询的分类ID有误");
        }
        return typeNameList;
    }

    @Override
    public Map<String,String> findTypeNameById(String typeIdList){
            Map<String,String> typeNameList=TypeDao.findTypeById(typeIdList);
            if(typeNameList.size()==0){
                throw new RuntimeException("查询的分类ID有误");
            }
            return typeNameList;
    }

    @Override
    public Result modifyArticleType(List<String> articleList, String typeId,String goalTypeId) {
        int defeat=0;
        int success=0;
        for(int i=0;i<articleList.size();i++){
            if(ArticleDao.findArticle(articleList.get(i))==null){
                defeat++;
                continue;
            }
            //记录次数
            System.out.println(articleList.get(i)+"|"+TypeDao.modifyArticleType(articleList.get(i),typeId,goalTypeId));
            if(TypeDao.modifyArticleType(articleList.get(i),typeId,goalTypeId)==false) defeat++;
            else success++;
        }
        if(defeat==0){
            result.setStatus(true);
            result.setData("全部更改成功");
        }
        else {
            result.setStatus(false);
            result.setData("只有"+success+"个更改成功，剩下" + defeat + "个无法正确更改");
        }
        return result;
    }

    @Override
    public boolean addType(String typeName) {
        String new_id = "typeN".concat(TypeDao.selectCount().toString());
        if (TypeDao.insertType(new_id,typeName)) {
            return true;
        } else throw new RuntimeException("文章分类添加失败");
    }

    @Override
    public boolean deleteType(String typeId) {
        if (TypeDao.deleteType(typeId)) {
            return true;
        } else throw new RuntimeException("文章分类名删除失败");
    }

    @Override
    public boolean modifyType(String typeId, String typeName) {
        if (TypeDao.modifyType(typeId,typeName)) {
            return true;
        } else throw new RuntimeException("文章分类名修改失败");
    }
}
