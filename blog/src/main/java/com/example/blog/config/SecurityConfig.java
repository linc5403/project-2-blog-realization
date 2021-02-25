package com.example.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;

  public SecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /**
   * 父类的缺省配置是:
   *
   * <pre>http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
   * </pre>
   *
   * 主要是配置public的endpoint, 以及对应url的权限
   *
   * @param http - security config
   * @throws Exception - other exceptions
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 去使能csrf校验
    http.csrf().disable();
    // 去使能httpBasic认证
    http.httpBasic().disable();

    http.formLogin()
        // 配置从哪个url的请求中获取用户名, 密码并进行认证
        .loginProcessingUrl("/login")
        .successHandler(successHandler())
        .failureHandler(failureHandler());

    // 配置访问权限
    http.authorizeRequests().anyRequest().permitAll();
    //    http.authorizeRequests().antMatchers("/blog/**").hasRole("User");
  }

  private AuthenticationSuccessHandler successHandler() {
    return (httpServletRequest, httpServletResponse, authentication) -> {
      httpServletResponse.getWriter().append("OK");
      httpServletResponse.setStatus(200);
    };
  }

  private AuthenticationFailureHandler failureHandler() {
    return (httpServletRequest, httpServletResponse, e) -> {
      httpServletResponse.getWriter().append("Authentication failure");
      httpServletResponse.setStatus(401);
    };
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  @Bean
  PasswordEncoder encoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
