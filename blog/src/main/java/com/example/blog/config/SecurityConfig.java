package com.example.blog.config;

import com.example.blog.bean.User;
import com.example.blog.service.UserService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  public SecurityConfig(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser("aaa").password("{noop}aaa").roles("USER");
    auth.userDetailsService(new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        // 根据自定义的user对象生成UserDetails对象
        return new UserDetailsImp(user);
      }
    }).passwordEncoder(NoOpPasswordEncoder.getInstance());
  }
}

class UserDetailsImp implements UserDetails {

  private final User user;

  UserDetailsImp(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var roles = new ArrayList<GrantedAuthority>();
    for (var role : user.getRoles()) {
      roles.add((GrantedAuthority) () -> "ROLE_" + role);
    }
    return roles;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.getActivated();
  }
}
