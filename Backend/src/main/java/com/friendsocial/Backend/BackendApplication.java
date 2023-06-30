package com.friendsocial.Backend;

import com.friendsocial.Backend.profile.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RestController
public class BackendApplication {

	public static void main(String[] args) {
	  SpringApplication.run(BackendApplication.class, args);
	}

	// Get mapping because we want to get something out from our server
	@GetMapping
	public List<Profile> hello() {
	  return List.of(
	          new Profile(
                    "c@gmail.com",
                    "connorfitz429",
                    "svsb",
                    24,
                    "Connor",
                    "Fitzpatrick",
                    "./HERE.png",
                    "MY APP",
                    LocalDateTime.now()
            )
    );
  }

}
