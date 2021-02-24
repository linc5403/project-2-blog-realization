package com.example.blog;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan
@Slf4j
@EnableAsync
public class BlogApplication {

  public static void main(String[] args) {
    var app = SpringApplication.run(BlogApplication.class, args);

    //    MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource =
    //        new MysqlConnectionPoolDataSource();
    SqlSessionFactory factory = app.getBean(SqlSessionFactory.class);
    System.out.println(factory);
    MysqlDataSource dataSource = app.getBean(MysqlDataSource.class);
    System.out.println(dataSource);
  }
}
