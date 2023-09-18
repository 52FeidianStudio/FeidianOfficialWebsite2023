package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontUserRegisterDTO {
    private Long id;
    private String name;
    private String sex;
    private String phone;
    private String email;
    private Long studentId;
    private Long facultyId;
    private String className;
    private String nationality;

}
