package com.feidian.bo;

import com.feidian.po.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompleteRegisterBO {

    private User user;

    private Register register;

    private Faculty faculty;

    private Subject subject;

    private Department department;

}
