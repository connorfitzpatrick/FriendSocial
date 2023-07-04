package com.friendsocial.Backend.post;

import com.friendsocial.Backend.profile.Profile;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import java.util.logging.Logger;



@Configuration
public class PostConfig {
  private static final Logger logger = Logger.getLogger(PostConfig.class.getName());

  @Autowired
  private ProfileRepository profileRepository;

  @Bean
  CommandLineRunner commandLineRunner(
          PostRepository repository) {
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

      repository.saveAll(
              List.of(connorsFirst, connorsSecond, alexFirst, alexSecond)
      );
    };
  }
}
