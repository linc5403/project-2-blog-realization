package com.example.blog.dao;

import com.example.blog.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
  public User getUserById(Integer id);

  public User getUserByName(String username);

  public Integer addUser(User user);

  public List<Integer> getRoleIdsByNames(List<String> roles);

  public Integer addUserRole(Integer userId, Integer roleId);
}
