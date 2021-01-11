package com.example.blog.security;

public class SecurityConstants {
  public static final String SECRET = "MyBlog";
  public static final Long EXPIRATION_TIME = 5 * 3600 * 1000L; // 5小时
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
}
