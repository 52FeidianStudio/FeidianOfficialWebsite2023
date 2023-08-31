package com.feidian.config;


import com.feidian.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
                .antMatchers("/register").anonymous()
                .antMatchers("/sendEmail").anonymous()
                .antMatchers("/verifyEmail").anonymous()
                .antMatchers("/graduate/getMessage").anonymous()
                .antMatchers("/department/getAllName").anonymous()
                .antMatchers("/faculty/getAllName").anonymous()
                .antMatchers("/subject/getSubjectNameByFaculty").anonymous()
                .antMatchers("/department/getByName").anonymous()
                .antMatchers("/forgetPassword").anonymous()
                .antMatchers("/verifyProcess").anonymous()
                .antMatchers("/department/getAllMessage").anonymous()
//                .antMatchers("/logout").authenticated()
//                .antMatchers("/addComment").authenticated()
//                .antMatchers("/user/userInfo").authenticated()
//                .antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().permitAll();
                .anyRequest().authenticated();

        //配置统一异常处理器
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);

        http.logout().disable();
        //将自定义的登录校验过滤器加到过滤器链中
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
