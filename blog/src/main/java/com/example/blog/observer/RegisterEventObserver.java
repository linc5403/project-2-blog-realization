package com.example.blog.observer;

import com.example.blog.bean.RegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class RegisterEventObserver {
  @EventListener
  @Async
  public void SendMail(RegisterEvent event) {
    var user = event.getUser();
    log.info("Sending mail to " + user.getEmail());
  }
}
