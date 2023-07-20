package com.feiidan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.feidian.po.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private SysUser user;

    //存储权限信息
    private List<String> permissions;

    private HashMap<String, String> token;

    public LoginUser(SysUser user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }


    //存储SpringSecurity所需要的权限信息的集合
    //在序列化时，将这个集合忽略。 原因时：出于安全考虑，redis在存储数据时不会将GrantedAuthority存进去，会报错。
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    @Override
    public  Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //账号是否过期，true指没有过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账号是否锁定，true表示账号没有被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //用户的凭证（密码）是否过期，true表示没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //用户是否启用，true表示用户是启用的
    @Override
    public boolean isEnabled() {
        return true;
    }
}
