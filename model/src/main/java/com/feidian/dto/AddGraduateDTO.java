package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddGraduateDTO {

    // 姓名
    @NotNull(message = "姓名不能为空")
    private String name;

    // 学院ID
    @NotNull(message = "学院ID不能为空")
    private Long facultyId;

    // 专业
    @NotNull(message = "专业ID不能为空")
    private long subjectId;

    // 部门ID
    @NotNull(message = "部门ID不能为空")
    private Long departmentId;

    // 城市
    @NotNull(message = "城市不能为空")
    private String city;

    // 公司
    @NotNull(message = "公司不能为空")
    private String company;

    // 创建者
    @NotNull(message = "创建人不能为空")
    private String createBy;

}
