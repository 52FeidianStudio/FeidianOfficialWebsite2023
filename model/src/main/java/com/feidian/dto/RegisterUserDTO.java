package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "用户名不能为空")
    private String username; // 用户名

    @NotBlank(message = "密码不能为空")
    private String password; // 密码

    @NotBlank(message = "姓名不能为空")
    private String name; // 姓名

    @NotBlank(message = "昵称不能为空")
    private String nickname; // 昵称

    @NotBlank(message = "年级不能为空")
    private String gradeName; // 昵称

    @NotBlank(message = "生日不能为空")
    private String birthday; // 生日（格式：YYYY-MM-DD）

    @NotBlank(message = "性别不能为空")
    private String sex; // 性别（0代表男性，1代表女性）

    @NotBlank(message = "学号不能为空")
    private long studentId; // 学号

    @NotBlank(message = "班级名称不能为空")
    private String className; // 班级名称

    @NotBlank(message = "专业不能为空")
    private String subject; // 专业


    @NotBlank(message = "想加入的部门不能为空")
    private String department; // 想加入的部门

    @NotBlank(message = "民族不能为空")
    private String nationality; // 民族

    @NotBlank(message = "联系电话不能为空")
    private String phone; // 联系电话

    @NotBlank(message = "邮箱不能为空")
    private String email; // 邮箱

    @NotBlank(message = "QQ号不能为空")
    private String qq; // QQ号

    @NotBlank(message = "验证码不能为空")
    private String code; // 验证码

}
