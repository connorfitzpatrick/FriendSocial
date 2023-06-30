package com.friendsocial.Backend.profile;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ProfileConfig {
  @Bean
  CommandLineRunner commandLineRunner(
          ProfileRepository repository) {
    return args -> {
      Profile connor = new Profile(
              "c@gmail.com",
              "connorfitz429",
              "svsb",
              LocalDate.of(1999, 4, 29),
              "Connor",
              "Fitzpatrick",
              "./HERE.png",
              "MY APP",
              LocalDateTime.now()
      );
      Profile alex = new Profile(
              "alex@gmail.com",
              "alex3",
              "aerkg",
              LocalDate.of(2000, 12, 25),
              "Alex",
              "Apple",
              "./HERE.png",
              "I Alex",
              LocalDateTime.now()
      );

      repository.saveAll(
              List.of(connor, alex)
      );
    };
  }
}
