package com.example.blog.bean;

import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {
  private final User user;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *     associated (never {@code null})
   */
  public RegisterEvent(Object source, User user) {
    super(source);
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
