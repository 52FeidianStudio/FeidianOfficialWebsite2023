package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditGraduateDTO {
    private Long graduateId;
    private String name;
    private Long facultyId;
    private Long subjectId;
    private Long departmentId;
    private String city;
    private String company;
}
