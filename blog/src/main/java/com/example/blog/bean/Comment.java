package com.example.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
public class Comment implements Serializable {
  private int id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdTime;

  private String content;
  private User commenter;
  private Blog blog;

  @Override
  public String toString() {
    return "Comment{"
        + "id="
        + id
        + ", createdTime="
        + createdTime
        + ", content='"
        + content
        + '\''
        + ", commenter="
        + commenter
        //        + ", blog="
        //        + blog.hashCode()
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comment comment = (Comment) o;
    return id == comment.id
        && createdTime.equals(comment.createdTime)
        && content.equals(comment.content)
        && commenter.equals(comment.commenter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdTime, content, commenter);
  }
}
