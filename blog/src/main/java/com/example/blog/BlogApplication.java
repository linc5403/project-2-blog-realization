package com.example.blog;

import com.example.blog.dao.BlogDao;
import com.example.blog.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan
@Slf4j
public class BlogApplication {

  public static void main(String[] args) {
    var app = SpringApplication.run(BlogApplication.class, args);
    var userDao = app.getBean(UserDao.class);
    var user = userDao.getUserByName("王二");
    log.debug(user.toString());
    var user2 = userDao.getUserById(2);
    log.debug(user2.toString());
    var blogDao = app.getBean(BlogDao.class);
    var blog1 = blogDao.getBlogById(13);
    log.debug(blog1.toString());

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
