package com.example.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Comment {
  private int id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdTime;

  private String content;
  private User commenter;
  private Blog blog;
}
