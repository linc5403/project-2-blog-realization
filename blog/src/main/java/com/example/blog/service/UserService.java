package com.example.blog.service;

import com.example.blog.bean.User;
import com.example.blog.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  User getUserById(Integer id) {
    return userDao.getUserById(id);
  }

  User getUserByName(String username) {
    return userDao.getUserByName(username);
  }
}
