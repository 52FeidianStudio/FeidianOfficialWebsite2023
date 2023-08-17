package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOperDTO {

    //查询操作
    private Integer queryCategoryId;

    private String gradeName;
    private Long subjectId;
    private Long desireDepartmentId;
    private String status;

    //审核操作
    private Long registerId;
    private String isApprovedFlag;
}
