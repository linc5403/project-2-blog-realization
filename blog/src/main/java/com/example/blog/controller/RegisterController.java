package com.example.blog.controller;

import com.example.blog.bean.RegisterEvent;
import com.example.blog.bean.User;
import com.example.blog.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class RegisterController {

  private final UserService userService;
  private final ApplicationEventPublisher publisher;

  public RegisterController(UserService userService, ApplicationEventPublisher publisher) {
    this.userService = userService;
    this.publisher = publisher;
  }

  @PostMapping("/register")
  ResponseEntity<?> register(
      @RequestParam String username, @RequestParam String password, @RequestParam String email) {

    var user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    user.setActivated(false);

    // 注册用户的接口统一加上User的角色
    user.setRoles(Collections.singletonList("User"));

    Boolean r = userService.addUser(user);
    if (r) {
      // 通知用户注册的事件
      RegisterEvent registerEvent = new RegisterEvent(this, user);
      publisher.publishEvent(registerEvent);
      return ResponseEntity.ok("OK");
    } else {
      // 应该返回更为详细的原因
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名或电子邮件已存在");
    }
  }
}
