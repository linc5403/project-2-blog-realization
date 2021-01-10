package com.example.blog.service;

import com.example.blog.bean.User;
import com.example.blog.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public User getUserById(Integer id) {
    return userDao.getUserById(id);
  }

  public User getUserByName(String username) {
    return userDao.getUserByName(username);
  }

  @Transactional
  public Boolean addUser(User user) {
    // 1. 获取用户权限对应的roleID
    Integer insertSuccess = userDao.addUser(user);
    if (1 != insertSuccess) {
      return false;
    }
    // 插入成功, 还需要写入权限
    List<Integer> roleIds = userDao.getRoleIdsByNames(user.getRoles());
    // 增加这个用户的所有权限

    // 应该使用mybatis的批量添加来完成, 不过太过繁琐, 先这么实现
    for (Integer roleId : roleIds) {
      userDao.addUserRole(user.getId(), roleId);
    }

    return true;
  }
}
