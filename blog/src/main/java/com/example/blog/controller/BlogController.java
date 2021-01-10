package com.example.blog.controller;

import com.example.blog.bean.Blog;
import com.example.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

  private final BlogService blogService;

  public BlogController(BlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping("/blog/{id}")
  ResponseEntity<?> getBlog(@PathVariable Integer id) {
    Blog blog = blogService.getBlogDetails(id);
    if (blog == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such blog: " + id);
    } else {
      return ResponseEntity.ok(blog);
    }
  }
}
