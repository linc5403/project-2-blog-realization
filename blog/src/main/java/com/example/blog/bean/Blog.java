package com.example.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class Blog implements Serializable {
  private Integer id;
  private String title;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdTime;

  private User author;
  private List<Comment> comments;

  @Override
  public String toString() {
    return "Blog{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", content='"
        + content
        + '\''
        + ", createdTime="
        + createdTime
        + ", author="
        + author
        //        + ", comments="
        //        + comments.hashCode()
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Blog blog = (Blog) o;
    return id.equals(blog.id)
        && title.equals(blog.title)
        && content.equals(blog.content)
        && createdTime.equals(blog.createdTime)
        && Objects.equals(author, blog.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, content, createdTime, author);
  }
}
