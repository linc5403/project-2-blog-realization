package com.example.blog;

import com.example.blog.dao.BlogDao;
import com.example.blog.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.util.Date;

@SpringBootApplication
@MapperScan
@Slf4j
public class BlogApplication {

  public static void main(String[] args) throws ParseException {
    var app = SpringApplication.run(BlogApplication.class, args);
    var userDao = app.getBean(UserDao.class);
    var user = userDao.getUserByName("王二");
    log.debug(user.toString());
    var user2 = userDao.getUserById(2);
    log.debug(user2.toString());
    var blogDao = app.getBean(BlogDao.class);
    var blog1 = blogDao.getBlogById(13);
    log.debug(blog1.toString());
    var blogs = blogDao.findBlogsByReplier("aa", 1, 2);
    System.out.println(blogs);
    blogs = blogDao.findBlogsByDate(new Date(), 0, 5);
    System.out.println(blogs.size());

    /*
    user.setUsername("liSi");
    user.setEmail("liSi@abc.com");
    Integer r = userDao.addUser(user);
    log.info(r.toString());
    log.info(user.toString());
     */

    //    List<Integer> ids = userDao.getRoleIdsByNames(Arrays.asList("User", "Admin"));
    //    log.info(ids.toString());

  }
}
