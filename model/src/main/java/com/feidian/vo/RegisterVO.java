package com.feidian.vo;

import com.feidian.po.Register;
import com.feidian.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterVO {

    private User user;

    private Register register;
}
