package com.example.blog.observer;

import com.example.blog.bean.RegisterEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsSender implements ApplicationListener<RegisterEvent> {
  @Override
  @Async
  public void onApplicationEvent(RegisterEvent event) {
    var user = event.getUser();
    // 发短信
    log.info(Thread.currentThread().getName() + ": send sms!!!!!!");
  }
}
