package com.example.blog.bean;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
  private int id;
  private Date createdTime;
  private String content;
  private User commenter;
  private Blog blog;
}
