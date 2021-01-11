package com.example.blog.observer;

import com.example.blog.bean.RegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterEventObserver {
  @EventListener
  @Async
  public void SendMail(RegisterEvent event) {
    var user = event.getUser();
    log.info("!!!!!!!!!!!!!!Sending mail to " + user.getEmail());
  }
}
