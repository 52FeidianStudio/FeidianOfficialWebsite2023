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
@TableName(value = "register")
public class Register {

  @TableId
  private Long id;
  private String username;
  //想要申请的组别
  private Long desireDepartmentId;
  //未来发展方向
  private String direction;
  //大学四年整体规划
  private String arrangement;
  //申请状态(0表示已提交，1表示已查看，2表示已通过，3表示未通过)
  private String status;
  //申请理由
  private String reason;
  //大头照
  private String imgUrl;
  
  private Long isDeleted;
  private java.sql.Timestamp createTime;
  private String createBy;
  private java.sql.Timestamp updateTime;
  private String updateBy;

}
