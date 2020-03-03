package com.pztws.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"typeListString","typeNameList","userId"})
public class Article {
    private String articleId;
    private String title;
    private String shortBorder;
    private String border;
    private List<String> typeIdList; //一定要显示的
    private List<String> typeNameList;//不显示的
    private String typeListString;//做typelist的中转，不显示
    private String userId;
    private String editor;
    private Integer indexStatus;
    private String dateNow;
    private String picUrl;


//    public Article(String title,String shortBorder,String border,ArrayList<String>  typeList,
//            String picUrl,Integer indexStatus){
//        this.title = title;
//        this.shortBorder = shortBorder;
//        this.border = border;
//        this.typeList = typeList;
//        this.picUrl = picUrl;
//        this.indexStatus = indexStatus;
//    }
}
