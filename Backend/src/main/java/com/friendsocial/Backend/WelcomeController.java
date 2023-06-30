package com.friendsocial.Backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
  @GetMapping("/api/test")
  public String welcome() {
    return "Welcome to FriendSocial!";
  }
}
