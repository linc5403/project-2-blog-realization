package com.example.blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
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

import static com.example.blog.security.SecurityConstants.*;

@Slf4j
public class JWTTokenAuthenticationFilter extends BasicAuthenticationFilter {

  public JWTTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    String header = req.getHeader(HEADER_STRING);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    // 从Token中解析出AuthenticationToken, 并放入SecurityContextHolder
    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);

    log.info(token);

    if (token != null) {
      // parse the token.

      DecodedJWT decodedJWT =
          JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
              .build()
              .verify(token.replace(TOKEN_PREFIX, ""));

      String user = decodedJWT.getSubject();
      if (user != null) {
        List<GrantedAuthority> auths = new ArrayList<>();
        decodedJWT.getClaim("roles").asList(String.class).forEach(a -> auths.add(() -> a));
        return new UsernamePasswordAuthenticationToken(user, null, auths);
      }
    }
    return null;
  }
}
