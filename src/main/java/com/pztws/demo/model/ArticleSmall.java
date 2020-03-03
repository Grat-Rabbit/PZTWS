package com.pztws.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleSmall {
    private String articleId;
    private String title;
    private String shortBorder;
    private String picUrl;


}
