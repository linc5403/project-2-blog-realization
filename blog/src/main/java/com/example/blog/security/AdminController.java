package com.example.blog.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AdminController {
  @GetMapping("/admin")
  String showAdminInfo(Principal principal) {
    return String.format("Hello, ADMIN %s!", principal.getName());
  }
}
