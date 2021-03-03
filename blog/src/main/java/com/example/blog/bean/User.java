package com.example.blog.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {
  private Integer id;
  private String username;
  private String password;
  private String email;
  private Boolean activated;
  private List<String> roles;
}
