package com.feidian.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

  private Long id;
  private String departmentName;
  private String introduction;
  
  private Long isDeleted;
  private java.sql.Timestamp createTime;
  private String createBy;
  private java.sql.Timestamp updateTime;
  private String updateBy;

}
