package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditDepartmentIntroductionDTO {
    private Long departmentId;

    private String introduction; //各组介绍
}
