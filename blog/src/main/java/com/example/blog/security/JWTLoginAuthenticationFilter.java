package com.example.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class JWTLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JWTLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    var gson = new Gson();
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    Map<String, Object> map = new HashMap<>();
    map.put("code", -1);
    map.put("status", "failed");
    map.put("desc", "用户名或密码不正确");
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.getWriter().write(gson.toJson(map));
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    // 认证成功返回生成的token
    List<String> roles = new ArrayList<>();
    authResult.getAuthorities().forEach(a -> roles.add(a.getAuthority()));

    String token =
        JWT.create()
            .withSubject(
                ((org.springframework.security.core.userdetails.UserDetails)
                        authResult.getPrincipal())
                    .getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .withClaim("roles", roles)
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }
}
