package com.example.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    //    super.successfulAuthentication(request, response, chain, authResult);
    // 从authResult中获取用户信息，生成token，返回给client
    var userDetails = (UserDetails) authResult.getPrincipal();
    String username = userDetails.getUsername();
    System.out.println(username);
    var roles = new ArrayList<String>();
    for (var auth : authResult.getAuthorities()) {
      roles.add(auth.getAuthority().replace("ROLE_", ""));
    }
    System.out.println(roles);
    // 生成token
    String token = null;
    try {
      Algorithm algorithm = Algorithm.HMAC256("PASSWORD");
      token =
          JWT.create()
              .withClaim("username", username)
              .withClaim("roles", roles)
              .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000 * 12))
              .sign(algorithm);
    } catch (JWTCreationException exception) {
      System.out.println(exception.toString());
    }
    System.out.println(token);
    response.setStatus(HttpStatus.OK.value());
    response.setContentType("application/json, charset=utf-8");
    response.getWriter().write(token);
    //    chain.doFilter(request, response);
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    super.unsuccessfulAuthentication(request, response, failed);
  }
}
