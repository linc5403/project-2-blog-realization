package com.example.blog.service;

import com.example.blog.bean.Blog;
import com.example.blog.dao.BlogDao;
import org.springframework.stereotype.Service;

@Service
public class BlogService {
  private final BlogDao blogDao;

  public BlogService(BlogDao blogDao) {
    this.blogDao = blogDao;
  }

  public Blog getBlogDetails(Integer id) {
    return blogDao.getBlogById(id);
  }

  public Boolean deleteBlogById(Integer id) {
    return blogDao.deleteBlogById(id) == 1;
  }

  public void addBlog(Blog blog) {
    blogDao.addBlog(blog);
  }

  public Boolean updateBlog(Integer id, String title, String content) {
    return blogDao.updateBlog(id, title, content) == 1;
  }
}
