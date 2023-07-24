package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDTO {


    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "生日不能为空")
    private String birthday;

    @NotBlank(message = "性别不能为空")
    private String sex;

    @NotNull(message = "学号不能为空")
    private long studentId;

    @NotBlank(message = "班级名称不能为空")
    private String className;

    @NotBlank(message = "专业不能为空")
    private String subject;

    @NotNull(message = "学院ID不能为空")
    private long facultyId;

    @NotNull(message = "部门ID不能为空")
    private long departmentId;

    @NotBlank(message = "民族不能为空")
    private String nationality;

    @NotBlank(message = "联系电话不能为空")
    private String phone;

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "QQ号不能为空")
    private String qq;

    @NotNull(message = "权限ID不能为空")
    private long roleId;

}