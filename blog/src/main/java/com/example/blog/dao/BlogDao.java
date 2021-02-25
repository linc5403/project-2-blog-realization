package com.example.blog.dao;

import com.example.blog.bean.Blog;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlogDao {
  public Blog getBlogById(int id);

  public int deleteBlogById(int id);

  public void addBlog(Blog blog);

  public int updateBlog(Integer id, String title, String content);

  public List<Blog> findBlogsByReplier(String replierName, Integer offset, Integer limit);

  public List<Blog> findBlogsByDate(Date date, Integer offset, Integer limit);
}
