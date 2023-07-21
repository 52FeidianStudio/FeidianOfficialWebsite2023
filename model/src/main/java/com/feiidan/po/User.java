package com.feiidan.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user")
public class User {

  @TableId
  private Long id;
  private String username;
  private String password;
  private String name;
  private String nickname;
  private String birthday;
  private String sex;
  private Long studentId;
  private String className;
  private String subject;
  private Long facultyId;
  private Long departmentId;
  private String nationality;
  private String phone;
  private String email;
  private String qq;
  
  private Long isDeleted;
  private java.sql.Timestamp createTime;
  private String createBy;
  private java.sql.Timestamp updateTime;
  private String updateBy;


}
