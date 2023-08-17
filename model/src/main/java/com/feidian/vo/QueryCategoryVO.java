package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryCategoryVO {

    private String gradeName;

    private Long subjectId;
    private String subjectName;

    private Long desireDepartmentId;
    //1:前端 2:后端 3:ios 4:信息安全
    private String departmentName;

    private String status;
    //0:已提交 1:已查看 2:已通过 3:未通过
    private String statusName;
}
