package com.friendsocial.Backend.profile;

import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.comment.CommentRepository;
import com.friendsocial.Backend.friend.Friend;
import com.friendsocial.Backend.friend.FriendRepository;
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
              "https://i.imgur.com/PCXFcu2.jpg",
              "MY APP",
              LocalDateTime.now()
      );
      Profile alex = new Profile(
              "alexrodriguez@gmail.com",
              "arod",
              "aerkg",
              LocalDate.of(2000, 12, 25),
              "Alex",
              "Rodriguez",
              "https://i.imgur.com/FVJF2bb.jpg",
              "I Alex",
              LocalDateTime.now()
      );
      Profile kieran = new Profile(
              "kieran@gmail.com",
              "kieran620",
              "bbbbbbb",
              LocalDate.of(1995, 06, 20),
              "Kieran",
              "Thomas",
              "https://i.imgur.com/79OV5FI.png",
              "Dummy data account",
              LocalDateTime.now()
      );

      repository.saveAll(
              List.of(connor, alex, kieran)
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
              profileId,
              "Text",
              "This is my first post.",
              Instant.now(),
              ""
      );
      Post connorsSecond = new Post(
              profileId,
              "Text",
              "This is my second post.",
              Instant.now(),
              ""
      );
      Post alexFirst = new Post(
              2L,
              "Text",
              "Ayo I am Alex.",
              Instant.now(),
              ""
      );
      Post alexSecond = new Post(
              3L,
              "Image",
              "This is a selfie of me, Connor.",
              Instant.now(),
              "../imageStore/image3"
      );
      Post connorsThird = new Post(
              profileId,
              "Text",
              "This is long post to check and see how the information I put down is formatted within the Angular component. I hope" +
                      "that this looks okay! If it doesn't I'll be up all night. figure out how to send pictures next " +
                      "as well as figure out image-post-components.",
              Instant.now(),
              ""
      );
      Post connorsFourth = new Post(
              3L,
              "Text",
              "I'm hungry.",
              Instant.now(),
              ""
      );
      Post connorsFifth = new Post(
              2L,
              "Image",
              "Just more test data.",
              Instant.now(),
              "../imageStore/image3"
      );


      drepository.saveAll(
              List.of(connorsFirst, connorsSecond, alexFirst, alexSecond, connorsThird, connorsFourth, connorsFifth)
      );
    };
  }

  @Autowired
  private ProfileRepository friendRepository;

  @Bean
  CommandLineRunner commandLineRunner2(
          FriendRepository frepository) {
    return args -> {
      Long profileId = 1L;
      Long friendId = 2L;
      Long friend2Id = 3L;
      Optional<Profile> profileOptional = profileRepository.findById(profileId);
      Optional<Profile> profileOptional2 = profileRepository.findById(friendId);
      Optional<Profile> profileOptional3 = profileRepository.findById(friend2Id);
      Profile p = profileOptional.orElseThrow(() -> new RuntimeException("Profile not found"));
      Profile f = profileOptional2.orElseThrow(() -> new RuntimeException("Profile not found"));
      Profile f2 = profileOptional3.orElseThrow(() -> new RuntimeException("Profile not found"));
      Friend connorfriend = new Friend(
              1L,
              2L,
              LocalDate.now()
      );
      Friend connorfriend2 = new Friend(
              1L,
              3L,
              LocalDate.now()
      );

      frepository.saveAll(
              List.of(connorfriend, connorfriend2)
      );
    };
  }

  @Autowired
  private CommentRepository cRepository;

  @Bean
  CommandLineRunner commandLineRunner3(
          CommentRepository crepository) {
    return args -> {
      Comment comment1 = new Comment(
              2L,
              1L,
              "This is a comment",
              Instant.now()
      );
      Comment comment2 = new Comment(
              3L,
              1L,
              "This is another comment",
              Instant.now()
      );
      Comment comment3 = new Comment(
              1L,
              3L,
              "This is the third comment",
              Instant.now()
      );
      Comment comment4 = new Comment(
              1L,
              1L,
              "Too many Comments!",
              Instant.now()
      );
      Comment comment5 = new Comment(
              2L,
              4L,
              "Weird post bro",
              Instant.now()
      );
      Comment comment6 = new Comment(
              1L,
              1L,
              "This is the Fourth comment",
              Instant.now()
      );
      Comment comment7 = new Comment(
              1L,
              1L,
              "This is the Fifth comment",
              Instant.now()
      );
      Comment comment8 = new Comment(
              1L,
              1L,
              "This is the Sixth comment",
              Instant.now()
      );
      Comment comment9 = new Comment(
              1L,
              1L,
              "This is the Seventh comment",
              Instant.now()
      );
      Comment comment10 = new Comment(
              3L,
              1L,
              "This is the Eighth comment",
              Instant.now()
      );
      Comment comment11 = new Comment(
              2L,
              1L,
              "This is the Ninth comment",
              Instant.now()
      );
      Comment comment12 = new Comment(
              1L,
              1L,
              "This is the Tenth comment",
              Instant.now()
      );
      Comment comment13 = new Comment(
              1L,
              1L,
              "This is the Eleventh comment",
              Instant.now()
      );
      Comment comment14 = new Comment(
              3L,
              1L,
              "This is the Twelvth comment",
              Instant.now()
      );
      Comment comment15 = new Comment(
              2L,
              1L,
              "This is the Thirtheenth comment",
              Instant.now()
      );
      Comment comment16 = new Comment(
              1L,
              1L,
              "This is the Fourtheenth comment",
              Instant.now()
      );

      crepository.saveAll(
              List.of(comment1,
                      comment2,
                      comment3,
                      comment4,
                      comment5,
                      comment6,
                      comment7,
                      comment8,
                      comment9,
                      comment10,
                      comment11,
                      comment12,
                      comment13,
                      comment14,
                      comment15,
                      comment16
              )
      );
    };
  }
}
