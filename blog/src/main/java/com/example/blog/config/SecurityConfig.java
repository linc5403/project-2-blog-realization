package com.example.blog.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 去使能csrf校验
    http.csrf().disable();
    // 配置从哪个url的请求中获取用户名, 密码并进行认证
    http.formLogin().loginProcessingUrl("/login");
  }
}
