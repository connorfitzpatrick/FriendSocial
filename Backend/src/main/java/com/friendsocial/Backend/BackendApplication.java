package com.friendsocial.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BackendApplication {

	public static void main(String[] args) {
	  SpringApplication.run(BackendApplication.class, args);
	}

	// Get mapping because we want to get something out from our server
	@GetMapping
	public String hello() {
	  return "Hello World";
  }

}
