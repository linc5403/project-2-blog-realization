package com.example.blog.dao;

import com.example.blog.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
  public User getUserById(Integer id);

  public User getUserByName(String username);
}
