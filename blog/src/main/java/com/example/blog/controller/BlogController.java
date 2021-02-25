package com.example.blog.controller;

import com.example.blog.bean.Blog;
import com.example.blog.bean.User;
import com.example.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

  private final BlogService blogService;

  public BlogController(BlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping("/{id}")
  ResponseEntity<?> getBlog(@PathVariable Integer id) {

    Blog blog = blogService.getBlogDetails(id);
    if (blog == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such blog: " + id);
    } else {
      return ResponseEntity.ok(blog);
    }
  }

  @DeleteMapping("/{id}")
  ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
    // TODO: 删除需要判断权限
    var deleted = blogService.deleteBlogById(id);
    if (deleted) {
      return ResponseEntity.ok(String.format("delete blog %d success", id));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(String.format("delete blog %d failed", id));
    }
  }

  @PostMapping
  ResponseEntity<?> postBlog(String content, String title, Integer userId) {
    // TODO: 发表需要登录
    User user = new User();
    user.setId(userId);
    var blog = new Blog();
    blog.setAuthor(user);
    blog.setContent(content);
    blog.setTitle(title);
    blogService.addBlog(blog);
    if (blog.getId() != null) {
      return ResponseEntity.ok(String.format("add %d blog success", blog.getId()));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("add blog failed");
    }
  }

  @PutMapping("/blog/{id}")
  ResponseEntity<?> editBlog(@PathVariable Integer id, String content, String title) {
    // TODO: 修改需要判断权限
    if (blogService.updateBlog(id, title, content)) {
      return ResponseEntity.ok(String.format("update %d blog success", id));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(String.format("update blog %d failed", id));
    }
  }
}
