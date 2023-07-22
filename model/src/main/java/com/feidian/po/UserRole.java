package com.feidian.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "user_role")
public class UserRole {

  private long userId;
  private long roleId;

  private long isDeleted;
  private java.sql.Timestamp createTime;
  private String createBy;
  private java.sql.Timestamp updateTime;
  private String updateBy;

}
