package com.friendsocial.Backend.profile;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Configuration
public class ProfileConfig {
  @Bean(name = "profileCommandLineRunner")
  CommandLineRunner profileConfig(
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


  @Autowired
  private ProfileRepository profileRepository;

  @Bean
  CommandLineRunner commandLineRunner1(
          PostRepository drepository) {
    return args -> {
      Long profileId = 1L;
      Optional<Profile> profileOptional = profileRepository.findById(profileId);
      Profile p = profileOptional.orElseThrow(() -> new RuntimeException("Profile not found"));
      Post connorsFirst = new Post(
              p,
              "Text",
              "This is my first post.",
              Instant.now(),
              ""
      );
      Post connorsSecond = new Post(
              p,
              "Text",
              "This is my first post.",
              Instant.now(),
              ""
      );
      Post alexFirst = new Post(
              p,
              "Text",
              "Ayo I am Alex.",
              Instant.now(),
              ""
      );
      Post alexSecond = new Post(
              p,
              "Image",
              "This is a selfie of me, Alex.",
              Instant.now(),
              "../imageStore/image3"
      );

      drepository.saveAll(
              List.of(connorsFirst, connorsSecond, alexFirst, alexSecond)
      );
    };
  }
}
