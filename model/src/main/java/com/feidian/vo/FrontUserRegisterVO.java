package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontUserRegisterVO {
    private String name;
    private String sex;
    private String phone;
    private String email;
    private Long studentId;
    private String facultyName;
    private String subjectName;
    private String className;
    private String nationality;
}
