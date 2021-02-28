package com.example.blog.utils;

import com.example.blog.bean.Blog;
import com.example.blog.bean.Comment;
import com.example.blog.bean.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class MapperUtilTest {

  @Test
  public void testArray1() throws IllegalAccessException {
    Blog[] blogs = new Blog[5];
    blogs[0] = new Blog();
    blogs[0].setTitle("000");
    blogs[1] = new Blog();
    blogs[1].setTitle("111");
    blogs[2] = new Blog();
    blogs[2].setTitle("222");
    blogs[3] = new Blog();
    blogs[3].setTitle("333");
    var out = MapperUtil.removeNullFields(blogs);
    //    System.out.println(out);
    Assertions.assertEquals("[{title=000}, {title=111}, {title=222}, {title=333}]", out.toString());
    System.out.println(MapperUtil.removeNullFields(blogs));
  }

  @Test
  public void testArray2() throws IllegalAccessException {
    var a = new int[10];
    for (int i = 0; i < 4; i++) a[i] = i;
    var out = MapperUtil.removeNullFields(a);
    for (int i = 0; i < 4; i++) Assertions.assertEquals(i, ((List) out).get(i));
  }

  @Test
  public void testPrimaryType() throws IllegalAccessException {
    var i = 1;
    var s = "abc";
    Assertions.assertEquals(1, MapperUtil.removeNullFields(i));
    Assertions.assertEquals("abc", MapperUtil.removeNullFields(s));
  }

  @Test
  public void testRecursion() throws IllegalAccessException {
    Blog blog = new Blog();
    blog.setTitle("abc");
    User blogger = new User();
    blogger.setUsername("张三");
    Comment comment = new Comment();
    comment.setCommenter(blogger);
    comment.setContent("这是一个评论");
    comment.setBlog(blog);
    blog.setAuthor(blogger);
    blog.setComments(new ArrayList<>(Collections.singletonList(comment)));
    /*var s = JSON.toJSON(blog);
    System.out.println(s);*/
    var out = MapperUtil.removeNullFields(blog);
    System.out.println(out);
  }

  @Test
  public void setTest() {
    Set<Object> s = new HashSet<>();
    changeSet(s);
    System.out.println(s);
  }

  private void changeSet(Set<Object> set) {
    set.add(1);
  }
}
