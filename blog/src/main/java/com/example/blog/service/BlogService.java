package com.example.blog.service;

import com.example.blog.bean.Blog;
import com.example.blog.dao.BlogDao;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
  private final BlogDao blogDao;

  public BlogService(BlogDao blogDao) {
    this.blogDao = blogDao;
  }

  public Blog getBlogDetails(Integer id) throws IllegalAccessException {
    var blog = blogDao.getBlogById(id);
    if (blog != null) {
      //      ObjectMapper mapper = new ObjectMapper();
      var fields = blog.getClass().getDeclaredFields();
      Map<String, Object> mapper = new HashMap<>();
      for (var field : fields) {
        field.setAccessible(true);
        if (field.get(blog) != null) {
          mapper.put(field.getName(), field.get(blog));
        }
      }
    }
    return blog;
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

  public List<Blog> findBlogsByReplier(String replierName, Integer pageSize, Integer pageNo) {
    Integer offset = (pageNo - 1) * pageSize;
    return blogDao.findBlogsByReplier(replierName, offset, pageSize);
  }

  public List<Blog> findByDate(Date date, Integer pageSize, Integer pageNo) {
    Integer offset = (pageNo - 1) * pageSize;
    return blogDao.findBlogsByDate(date, offset, pageSize);
  }
}
