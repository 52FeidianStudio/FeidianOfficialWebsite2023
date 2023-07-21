package com.feidian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarVO {

    private Integer code;
    private String msg;

    private String avatarUrl;


    public AvatarVO(String msg) {
        this.msg = msg;
    }

    public AvatarVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
