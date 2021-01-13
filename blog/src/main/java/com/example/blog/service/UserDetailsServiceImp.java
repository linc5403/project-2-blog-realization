package com.example.blog.service;

import com.example.blog.bean.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
@Primary
public class UserDetailsServiceImp implements UserDetailsService {
  private final UserService userService;

  public UserDetailsServiceImp(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.getUserByName(username);
    if (user == null) throw new UsernameNotFoundException("没有这个用户: " + username);
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

        user.getRoles()
            .forEach(
                a -> {
                  grantedAuthorities.add(() -> "ROLE_" + a);
                });
        return grantedAuthorities;
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
    };
  }
}
