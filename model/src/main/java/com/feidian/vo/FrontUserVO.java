package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontUserVO {
    private String name;
    private String nickname;
    private String birthday;
    private String sex;
    private Long studentId;
    private String gradeName;
    private String className;
    private String subjectName;
    private String facultyName;
    private String departmentName;
    private String avatarUrl;
    private String nationality;
    private String phone;
    private String email;
    private String qq;
}
