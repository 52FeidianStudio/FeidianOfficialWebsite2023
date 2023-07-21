package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduatesVO {
    private String name;
    private String faculty;
    private String subject;
    private String department;
    private String city;
    private String company;

}
