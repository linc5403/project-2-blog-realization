package com.example.blog.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Blog {
  private Integer id;
  private String title;
  private String content;
  private Date createdTime;
  private User author;
  private List<Comment> comments;
}
