package com.example.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenProcessFilter extends BasicAuthenticationFilter {
  public JwtTokenProcessFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    // 从请求的Header中获取token
    var token = request.getHeader("Authorization");

    if (token != null && token.startsWith("Bearer ")) {
      // 验证token的合法性
      token = token.substring(7);
      DecodedJWT jwt = null;
      try {
        Algorithm algorithm = Algorithm.HMAC256("PASSWORD");
        JWTVerifier verifier = JWT.require(algorithm).build(); // Reusable verifier instance
        jwt = verifier.verify(token);
      } catch (JWTVerificationException exception) {
        // Invalid signature/claims
        System.out.println("!!!!!!校验不通过");
      }

      if (jwt != null) {
        // 获取token中的字段
        var username = jwt.getClaim("username").asString();
        var roles = jwt.getClaim("roles").asList(String.class);
        if (username == null || roles == null) {
          chain.doFilter(request, response);
          return;
        }

        System.out.println("username: " + username);
        System.out.println("roles" + roles);
        // 生成Authentication对象，放入SecurityContext
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (var role : roles) {
          authorities.add(() -> "ROLE_" + role);
        }

        UsernamePasswordAuthenticationToken authResult =
            new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authResult);
      }
    }
    chain.doFilter(request, response);
  }
}
