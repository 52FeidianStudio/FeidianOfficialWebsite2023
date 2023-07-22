package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private String nickname; // 昵称
    private String birthday; // 生日（格式：YYYY-MM-DD）
    private String sex; // 性别（0代表男性，1代表女性）
    private long studentId; // 学号
    private String className; // 班级名称
    private String subject; // 专业
    private long facultyId; // 学院ID
    private long departmentId; // 想加入的部门ID
    private String nationality; // 民族
    private String phone; // 联系电话
    private String email; // 邮箱
    private String qq; // QQ号

}
