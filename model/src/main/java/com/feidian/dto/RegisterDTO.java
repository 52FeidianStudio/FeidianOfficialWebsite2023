package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    //个人简介
    private String resume;
    //想要申请的组别
    private Long desireDepartmentId;
    //发展方向
    private String direction;
    //大学四年整体规划
    private String arrangement;
    //申请状态(0表示已提交，1表示已查看，2表示已通过，3表示未通过)
    private String status;
    //申请理由，对你所选择方向的了解以及为什么想选择此方向
    private String reason;
    //大头照
    private String imgUrl;

}
