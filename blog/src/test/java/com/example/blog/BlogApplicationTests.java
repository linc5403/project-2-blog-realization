package com.example.blog;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
class BlogApplicationTests {

  @Autowired private DataSource dataSource;

  @Test
  void contextLoads() {
    System.out.println(dataSource);
  }
}
