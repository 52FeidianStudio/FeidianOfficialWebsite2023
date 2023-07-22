package com.feidian.vo;

import com.feidian.bo.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVO {

    private LoginUser loginUser;
    private String jwt;


}
