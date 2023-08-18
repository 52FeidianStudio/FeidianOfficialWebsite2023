package com.feidian.vo;

import com.feidian.bo.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    // 用户id
    private Long userId;

    // 用户权限
    private List<String> permissions;

    // 登陆token
    private String token;


}
