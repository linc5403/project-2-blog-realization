package com.example.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Blog {
  private Integer id;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdTime;

  private User author;
  private List<Comment> comments;
}
