package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionalRegisterVO {

    private Long userId;
    private Long registerId;

    private String name;
    private String nickname;
    private String imgUrl;

    private Long subjectId;
    private String gradeName;

    //想要申请的组别
    private Long desireDepartmentId;
    //申请状态(0表示已提交，1表示已查看，2表示已通过，3表示未通过)
    private String status;

}
