package com.example.blog;

import com.example.blog.bean.Blog;
import com.example.blog.bean.Comment;
import com.example.blog.bean.User;
import com.example.blog.utils.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@MapperScan
@Slf4j
public class BlogApplication {

  public static void main(String[] args) throws IllegalAccessException {
    var app = SpringApplication.run(BlogApplication.class, args);
    /*var userDao = app.getBean(UserDao.class);
    var user = userDao.getUserByName("王二");
    log.debug(user.toString());
    var user2 = userDao.getUserById(2);
    log.debug(user2.toString());
    var blogDao = app.getBean(BlogDao.class);
    var blog1 = blogDao.getBlogById(226);
    log.debug(blog1.toString());
    var blogs = blogDao.findBlogsByReplier("aa", 1, 2);
    System.out.println(blogs);
    blogs = blogDao.findBlogsByDate(new Date(), 0, 5);
    System.out.println(blogs.size());
    var mapper = MapperUtil.removeNullFields(blog1);
    System.out.println(mapper);
    blog1 = blogDao.getBlogById(220);
    mapper = MapperUtil.removeNullFields(blog1);
    System.out.println(mapper);*/

    Blog blog = new Blog();
    blog.setTitle("abc");
    User blogger = new User();
    blogger.setUsername("张三");
    Comment comment = new Comment();
    comment.setCommenter(blogger);
    comment.setContent("这是一个评论");
    comment.setBlog(blog);
    blog.setAuthor(blogger);
    blog.setComments(new ArrayList<>(Collections.singletonList(comment)));
    //    var s = JSON.toJSON(blog);
    //    System.out.println(s);
    var out = MapperUtil.removeNullFields(blog);
    System.out.println(out);
  }
}
