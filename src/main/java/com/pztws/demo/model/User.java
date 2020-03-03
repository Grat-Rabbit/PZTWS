package com.pztws.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;


@Data
@JsonIgnoreProperties(value = {"password"}) //返回的时候，隐藏关键字
public class User {
        @JsonInclude(Include.NON_NULL)
    private String userId;
        @JsonInclude(Include.NON_NULL)
    private String phone;
    private String password;
    private String userName;
    private String userPic;
    private String departmentName;
        @JsonInclude(Include.NON_NULL)
    private String departmentId; //后续判断使用
    private String signature;
    private Integer level;
    private Integer grade;


}
