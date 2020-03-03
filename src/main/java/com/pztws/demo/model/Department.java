package com.pztws.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Department {
    String departmentId;
    String departmentName;
    String departmentPic;
    String departmentBrief;

}
