package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontUserVO {
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "生日不能为空")
    private String birthday;
    @NotBlank(message = "性别不能为空")
    private String sex;
    @NotBlank(message = "学号不能为空")
    private Long studentId;
    @NotBlank(message = "年级不能为空")
    private String gradeName;
    @NotBlank(message = "班级名称不能为空")
    @Pattern(regexp = "^[0-9]{4}$" ,message = "班级应是四位数字，例：2301")
    private String className;
    @NotBlank(message = "专业不能为空")
    private String subjectName;
    @NotBlank(message = "学院不能为空")
    private String facultyName;
    @NotBlank(message = "想加入的部门不能为空")
    private String departmentName;
    private String avatarUrl;
    private String nationality;
    private String phone;
    private String email;
    private String qq;
}
