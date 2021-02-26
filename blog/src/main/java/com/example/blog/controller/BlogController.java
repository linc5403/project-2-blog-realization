package com.example.blog.controller;

import com.alibaba.fastjson.JSON;
import com.example.blog.bean.Blog;
import com.example.blog.bean.User;
import com.example.blog.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@Api("博客接口")
@RequestMapping("/api")
public class BlogController {

  private final BlogService blogService;

  @Autowired
  public BlogController(BlogService blogService) {
    this.blogService = blogService;
  }

  @GetMapping("/blog/{id}")
  ResponseEntity<?> getBlog(@PathVariable Integer id) throws IllegalAccessException {

    Blog blog = blogService.getBlogDetails(id);

    if (blog == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such blog: " + id);
    } else {
      blog.setTitle(null);
      System.out.println(JSON.toJSON(blog));
      return ResponseEntity.ok(blog);
    }
  }

  @DeleteMapping("/blog/{id}")
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

  @PostMapping("/blog")
  ResponseEntity<?> postBlog(
      /*@ApiParam("用户ID") @RequestParam Integer userId,
      @ApiParam("博客标题") @RequestParam String title,
      @ApiParam("博客内容") @RequestParam String content) {*/
      @ApiParam Integer userId,
      @ApiParam String title,
      @ApiParam String content,
      ServletRequest request) {
    System.out.println(title + "....." + content + "...." + userId);
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

  @GetMapping("/blogs")
  @ApiOperation("博客搜索功能")
  ResponseEntity<?> searchBlogs(
      @ApiParam("博主名") @RequestParam(required = false) String bloggerName,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdTime,
      @RequestParam(defaultValue = "20") Integer pageSize,
      @RequestParam(defaultValue = "1") Integer pageNo,
      @RequestParam(required = false) String kwd,
      @RequestParam(required = false) String replierName) {
    List<Blog> blogs = null;
    if (replierName != null) {
      blogs = blogService.findBlogsByReplier(replierName, pageSize, pageNo);
    } else if (createdTime != null) {
      blogs = blogService.findByDate(createdTime, pageSize, pageNo);
    }

    if (blogs != null) {
      return ResponseEntity.ok(blogs);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("search failed");
    }
  }
}
