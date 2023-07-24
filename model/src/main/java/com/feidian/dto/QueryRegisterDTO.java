package com.feidian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryRegisterDTO {

    private String gradeName;
    private Long subjectId;
    private Long desireDepartmentId;
    private String status;
}
