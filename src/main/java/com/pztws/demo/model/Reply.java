package com.pztws.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reply {
    private String replyId;
    private Integer level;
    private String userName;
    private String userPic;
    private String datetime;
    private String body;
    private String userId;
}
