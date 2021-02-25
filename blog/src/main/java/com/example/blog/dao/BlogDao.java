package com.example.blog.dao;

import com.example.blog.bean.Blog;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogDao {
  public Blog getBlogById(int id);

  public int deleteBlogById(int id);

  void addBlog(Blog blog);

  public int updateBlog(Integer id, String title, String content);
}
