package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddGraduateDTO {

    // 姓名
    private String name;

    // 学院ID
    private Long facultyId;

    // 专业
    private long subjectId;

    // 部门ID
    private Long departmentId;

    // 城市
    private String city;

    // 公司
    private String company;

    // 是否删除（1代表是，0代表否）
    private Long isDeleted;

    // 创建时间
    private java.sql.Timestamp createTime;

    // 创建者
    private String createBy;

}
