package com.example.blog;

import com.example.blog.dao.UserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
public class BlogApplication {

  public static void main(String[] args) {
    var app = SpringApplication.run(BlogApplication.class, args);
    var userDao = app.getBean(UserDao.class);
    var user = userDao.getUserByName("王二");
    System.out.println(user);
  }
}
