package com.feidian.bo;

import com.feidian.po.Department;
import com.feidian.po.Faculty;
import com.feidian.po.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraduatesBO {
    private Long id;
    private String name;
    private Long facultyId;
    private Long subjectId;
    private Long departmentId;
    private String city;
    private String company;

    private Long isDeleted;
    private java.sql.Timestamp createTime;
    private String createBy;
    private java.sql.Timestamp updateTime;
    private String updateBy;
    private Department department;
    private Faculty faculty;
    private Subject subject;
}
